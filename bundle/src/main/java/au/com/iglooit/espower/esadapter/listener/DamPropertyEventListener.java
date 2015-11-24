package au.com.iglooit.espower.esadapter.listener;

import au.com.iglooit.espower.esadapter.core.ex.ESAdapterX;
import au.com.iglooit.espower.esadapter.service.es.ElasticClient;
import au.com.iglooit.espower.esadapter.service.index.IndexNodeHandler;
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

@Component(metatype = true)
public class DamPropertyEventListener implements EventListener {
    private final Logger LOGGER = LoggerFactory.getLogger(DamPropertyEventListener.class);
    @Reference
    private SlingRepository repository;

    @Reference
    private IndexNodeHandler indexNodeHandler;


    private Session session;
    private ObservationManager observationManager;

    protected void activate(ComponentContext context) throws Exception {
        session = repository.loginAdministrative(null);
        observationManager = session.getWorkspace().getObservationManager();

        observationManager.addEventListener(this, Event.PROPERTY_ADDED|Event.PROPERTY_CHANGED|Event.PROPERTY_REMOVED, EventListenerConstants.ASSET_PATH, true, null,
                EventListenerConstants.ASSET_LISTENER_TYPE, false);
        LOGGER.info("Added JCR event listener - Node Property");
    }

    protected void deactivate(ComponentContext componentContext) {
        try {
            if (observationManager != null) {
                observationManager.removeEventListener(this);
                LOGGER.info("Removed JCR event listener - Node Property");
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
        try {
            while (it.hasNext()) {
                Event event = it.nextEvent();
                LOGGER.info("DAM Property Event Listener - property event: ", event.getPath());

                Property property = session.getProperty(event.getPath());

                Node node = findParentDamNode(property.getParent());
                if (node == null) {
                    LOGGER.debug("Skip processing node: " + event.getPath());
                    return;
                }
                indexNodeHandler.updateNodeIndex(node);
            }
        } catch (RepositoryException re) {
            LOGGER.warn("ignore the content: " + re.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private Node findParentDamNode(Node node) throws RepositoryException {
        if (node == null) {
            LOGGER.error("can not find the page node.");
            throw new ESAdapterX("can not find the page node.");
        }
        if (EventListenerConstants.DAM_TYPE.equals(node.getPrimaryNodeType().getName())) {
            return node;
        } else {
            return findParentDamNode(node.getParent());
        }
    }
}
