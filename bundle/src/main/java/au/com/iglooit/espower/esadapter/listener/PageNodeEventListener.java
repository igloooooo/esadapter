package au.com.iglooit.espower.esadapter.listener;

import au.com.iglooit.espower.esadapter.core.dto.ConfigDTO;
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
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Created by nicholaszhu on 16/11/2015.
 */
@Component(metatype = true)
public class PageNodeEventListener implements EventListener {
    private final Logger LOGGER = LoggerFactory.getLogger(PageNodeEventListener.class);

    @Reference
    private ESAdapterConfigService esAdapterConfigService;
    @Reference
    private SlingRepository repository;
    @Reference
    private IndexNodeHandler indexNodeHandler;


    private Session session;
    private ObservationManager observationManager;

    protected void activate(ComponentContext context) throws Exception {
        session = repository.loginAdministrative(null);
        observationManager = session.getWorkspace().getObservationManager();

        observationManager.addEventListener(this, Event.NODE_ADDED | Event.NODE_REMOVED | Event.NODE_MOVED, EventListenerConstants.PAGE_PATH, true, null,
                EventListenerConstants.PAGE_LISTENER_TYPE, false);

        LOGGER.info("Added JCR event listener - Dam Node");
    }

    protected void deactivate(ComponentContext componentContext) {
        try {
            if (observationManager != null) {
                observationManager.removeEventListener(this);
                LOGGER.info("Removed JCR event listener - Add Node");
            }
        } catch (RepositoryException re) {
            LOGGER.error("Error removing the JCR event listener - Add Node", re);
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
                if (PathUtil.matchPath(configDTO.getEsConfigDTO().getListenerPath(), event.getPath())) {
                    Node node = session.getNode(event.getPath());

                    if (node == null) {
                        LOGGER.debug("Skip processing node: " + event.getPath());
                        return;
                    }
                    // only check the page
                    if (EventListenerConstants.PAGE_TYPE.equals(node.getPrimaryNodeType().getName())) {
                        switch (event.getType()) {
                            case Event.NODE_ADDED:
                                addNodeHandler(node);
                                break;
                            case Event.NODE_MOVED:
                                moveNodeHandler(node);
                                break;
                            case Event.NODE_REMOVED:
                                removeNodeHandler(node);
                                break;
                        }
                    }
                }

            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return;
    }

    private void addNodeHandler(Node node) throws RepositoryException, IOException {
        LOGGER.info("Dam Node Listener - new page add event: ", node.getPath());
        indexNodeHandler.addNodeIndex(node);
    }

    private void removeNodeHandler(Node node) throws RepositoryException, IOException {
        LOGGER.info("Dam Node Listener - remove page event: ", node.getPath());
        indexNodeHandler.deleteNodeIndex(node);
    }

    private void moveNodeHandler(Node node) throws RepositoryException, IOException {
        LOGGER.info("Dam Node Listener - move page event: ", node.getPath());
        indexNodeHandler.updateNodeIndex(node);
    }


}