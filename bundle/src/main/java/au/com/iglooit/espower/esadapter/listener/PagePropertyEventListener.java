package au.com.iglooit.espower.esadapter.listener;

import au.com.iglooit.espower.esadapter.core.dto.ConfigDTO;
import au.com.iglooit.espower.esadapter.core.ex.ESAdapterX;
import au.com.iglooit.espower.esadapter.service.ESAdapterConfigService;
import au.com.iglooit.espower.esadapter.service.es.ElasticClient;
import au.com.iglooit.espower.esadapter.service.index.IndexNodeHandler;
import au.com.iglooit.espower.esadapter.util.PathUtil;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;

/**
 * Created by nicholaszhu on 17/11/2015.
 */
@Component(metatype = true)
public class PagePropertyEventListener implements EventListener {
    private final Logger LOGGER = LoggerFactory.getLogger(PagePropertyEventListener.class);
    @Reference
    private SlingRepository repository;

    @Reference
    private IndexNodeHandler indexNodeHandler;

    @Reference
    private ESAdapterConfigService esAdapterConfigService;


    private Session session;
    private ObservationManager observationManager;

    protected void activate(ComponentContext context) throws Exception {
        session = repository.loginAdministrative(null);
        observationManager = session.getWorkspace().getObservationManager();

        observationManager.addEventListener(this, Event.PROPERTY_ADDED | Event.PROPERTY_CHANGED | Event.PROPERTY_REMOVED,
                EventListenerConstants.PAGE_PATH, true, null,
                EventListenerConstants.PAGE_LISTENER_TYPE, false);
        LOGGER.info("Added JCR Event listener - Page Property");
    }

    protected void deactivate(ComponentContext componentContext) {
        try {
            if (observationManager != null) {
                observationManager.removeEventListener(this);
                LOGGER.info("Removed JCR event listener - Page Property");
            }
        } catch (RepositoryException re) {
            LOGGER.error("Error removing the JCR event listener - Node Property", re);
        } finally {
            if (session != null) {
                session.logout();
                session = null;
            }
        }
    }

    @Override
    public void onEvent(EventIterator it) {
        ConfigDTO configDTO = esAdapterConfigService.getConfigDTO();
        try {
            while (it.hasNext()) {
                Event event = it.nextEvent();
                LOGGER.info("Page Property Event Listener - property event: ", event.getPath());
                Property property = session.getProperty(event.getPath());
                Node node = findParentPageNode(property.getParent());
                if (node == null) {
                    LOGGER.debug("Skip processing node: " + event.getPath());
                    return;
                }
                if (PathUtil.matchPath(configDTO.getEsConfigDTO().getListenerPath(), node.getPath())) {
                    indexNodeHandler.updateNodeIndex(node);
                }
            }
        } catch (RepositoryException re) {
            LOGGER.warn("ignore the content: " + re.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private Node findParentPageNode(Node node) throws RepositoryException {
        if (node == null) {
            LOGGER.error("can not find the page node.");
            throw new ESAdapterX("can not find the page node.");
        }
        if (EventListenerConstants.PAGE_TYPE.equals(node.getPrimaryNodeType().getName())) {
            return node;
        } else {
            return findParentPageNode(node.getParent());
        }
    }
}