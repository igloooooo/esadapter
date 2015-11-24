package au.com.iglooit.espower.esadapter.listener;

import au.com.iglooit.espower.esadapter.service.es.ElasticClient;
import au.com.iglooit.espower.esadapter.service.index.IndexNodeHandler;
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

@Component(metatype = true)
public class DamNodeEventListener implements EventListener {
    private final Logger LOGGER = LoggerFactory.getLogger(DamNodeEventListener.class);

    @Reference
    private SlingRepository repository;

    @Reference
    private IndexNodeHandler indexNodeHandler;


    private Session session;
    private ObservationManager observationManager;

    protected void activate(ComponentContext context) throws Exception {
        session = repository.loginAdministrative(null);
        observationManager = session.getWorkspace().getObservationManager();

        observationManager.addEventListener(this, Event.NODE_ADDED | Event.NODE_REMOVED | Event.NODE_MOVED, EventListenerConstants.ASSET_PATH, true, null,
                EventListenerConstants.ASSET_LISTENER_TYPE, false);
        LOGGER.info("Added JCR node listener - Dam Node");
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
        try {

            while (it.hasNext()) {
                Event event = it.nextEvent();
                switch (event.getType()) {
                    case Event.NODE_ADDED:
                        addNodeHandler(event);
                        break;
                    case Event.NODE_MOVED:
                        moveNodeHandler(event);
                        break;
                    case Event.NODE_REMOVED:
                        removeNodeHandler(event);
                        break;
                }

            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return;
    }

    private void addNodeHandler(Event event) throws RepositoryException, IOException {
        LOGGER.info("Dam Node Listener - new add event: ", event.getPath());

        Node node = session.getNode(event.getPath());

        if (node == null) {
            LOGGER.debug("Skip processing node: " + event.getPath());
            return;
        }
        indexNodeHandler.addNodeIndex(node);
    }

    private void removeNodeHandler(Event event) throws RepositoryException, IOException {
        LOGGER.info("Dam Node Listener - remove add event: ", event.getPath());
        Node node = session.getNode(event.getPath());

        if (node == null) {
            LOGGER.debug("Skip processing node: " + event.getPath());
            return;
        }
        indexNodeHandler.deleteNodeIndex(node);
    }

    private void moveNodeHandler(Event event) throws RepositoryException, IOException{
        LOGGER.info("Dam Node Listener - move add event: ", event.getPath());
        Node node = session.getNode(event.getPath());

        if (node == null) {
            LOGGER.debug("Skip processing node: " + event.getPath());
            return;
        }

        indexNodeHandler.updateNodeIndex(node);
    }
}
