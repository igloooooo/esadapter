package au.com.iglooit.espower.esadapter.contentbuilder;

import com.day.cq.replication.*;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.Map;

@Component(label = "Elastic Replication Content Builder", description = "This servlet replicates data to Elastic", immediate = true, enabled = true, metatype = true)
@Service(ContentBuilder.class)
public class ESContentBuilder implements ContentBuilder {
    @Reference
    private ResourceResolverFactory rrFactory;

    private static final Logger logger = LoggerFactory.getLogger(ESContentBuilder.class);

    public static final String NAME = "ES Adapter";

    public static final String TITLE = "ES Data Flush";

    /**
     * {@inheritDoc}
     */
    public ReplicationContent create(Session session, ReplicationAction action,
                                     ReplicationContentFactory factory)
            throws ReplicationException {

        /* In this simple example we check whether the page activated has
        no extension (e.g. /content/geometrixx/en/services) and add
        this page plus html extension to the list of URIs to re-fetch */

        String path = action.getPath();
        if (path != null) {
            logger.info("Push data - path: " + path);
        }
        return ReplicationContent.VOID;
    }

    @Override
    public ReplicationContent create(Session session,
                                     ReplicationAction replicationAction,
                                     ReplicationContentFactory replicationContentFactory,
                                     Map<String, Object> stringObjectMap) throws ReplicationException {
        if(stringObjectMap != null) {
            for(Map.Entry<String, Object> key : stringObjectMap.entrySet()) {
                logger.info("key is " + key.getKey());
            }
        }
        return null;
    }


    /**
     * {@inheritDoc}
     *
     * @return {@value #NAME}
     */
    public String getName() {
        return NAME;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@value #TITLE}
     */
    public String getTitle() {
        return TITLE;
    }
}
