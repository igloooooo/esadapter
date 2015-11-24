package au.com.iglooit.espower.esadapter.service;

import au.com.iglooit.espower.esadapter.core.dto.ConfigDTO;
import au.com.iglooit.espower.esadapter.core.dto.ESConfigDTO;
import au.com.iglooit.espower.esadapter.service.es.ElasticClient;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.commons.jcr.JcrUtil;
import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.value.StringValue;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by nicholaszhu on 9/11/2015.
 */
@Service(ESAdapterConfigService.class)
@Component(metatype = true)
public class ESAdapterConfigService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerFieldMappingService.class);
    private static final String CONFIG_NODE_PATH = "/etc/esadapterconfig/config";
    private static final String GEN_ES_ADDRESS = "esAddress";
    private static final String GEN_ES_PORT = "9200";
    private static final String ES_CONFIG_NODE_PATH = "esconfig";
    private static final String ES_CONFIG_CLUSTER_NAME = "clusterName";
    private static final String ES_CONFIG_CONNECT_TIMEOUT = "connectTimeout";
    private static final String ES_CONFIG_NODES_SAMPLER_INTERVAL = "nodesSamplerInterval";
    private static final String ES_CONFIG_SNIFF = "sniff";
    private static final String ES_CONFIG_THREADPOOL_SEARCH_TYPE = "threadpoolSearchType";
    private static final String ES_CONFIG_THREADPOOL_SEARCH_SIZE = "threadpoolSearchSize";
    private static final String ES_CONFIG_THREADPOOL_SEARCH_QUEUE_SIZE = "threadpoolSearchQueueSize";
    private static final String ES_CONFIG_LISTENER_PATH = "listenerPath";
    private static final String ES_CONFIG_START_LISTENER = "startListener";

    private ConfigDTO configDTO;

    @Reference
    private SlingRepository repository;

    private Session session;

    protected void activate(ComponentContext context) throws Exception {
        session = repository.loginAdministrative(null);
        configDTO = loadConfigDTO();
        LOGGER.info("Init ES Adapter Configuration.");
    }

    private ConfigDTO loadConfigDTO() {
        ConfigDTO dto = new ConfigDTO();
        try {
            Node node = JcrUtil.createPath(CONFIG_NODE_PATH, JcrConstants.NT_UNSTRUCTURED, session);
            if (node.hasProperty(GEN_ES_ADDRESS)) {
                dto.setEsAddress(node.getProperty(GEN_ES_ADDRESS).getString());
                dto.setEsPort((int)node.getProperty(GEN_ES_PORT).getLong());
                if (node.hasNode(ES_CONFIG_NODE_PATH)) {
                    Node esConfigNode = node.getNode(ES_CONFIG_NODE_PATH);
                    ESConfigDTO esConfigDTO = new ESConfigDTO();
                    esConfigDTO.setClusterName(esConfigNode.getProperty(ES_CONFIG_CLUSTER_NAME).getString());
                    esConfigDTO.setConnectTimeout(esConfigNode.getProperty(ES_CONFIG_CONNECT_TIMEOUT).getLong());
                    esConfigDTO.setNodesSamplerInterval(esConfigNode.getProperty(ES_CONFIG_NODES_SAMPLER_INTERVAL).getLong());
                    esConfigDTO.setSniff(esConfigNode.getProperty(ES_CONFIG_SNIFF).getBoolean());
                    esConfigDTO.setThreadpoolSearchSize(esConfigNode.getProperty(ES_CONFIG_THREADPOOL_SEARCH_SIZE).getLong());
                    esConfigDTO.setThreadpoolSearchType(esConfigNode.getProperty(ES_CONFIG_THREADPOOL_SEARCH_TYPE).getString());
                    esConfigDTO.setThreadpoolSearchQueueSize(esConfigNode.getProperty(ES_CONFIG_THREADPOOL_SEARCH_QUEUE_SIZE).getLong());
                    esConfigDTO.setStartListener(esConfigNode.getProperty(ES_CONFIG_START_LISTENER).getBoolean());
                    esConfigDTO.setListenerPath(generatePath(esConfigNode.getProperty(ES_CONFIG_LISTENER_PATH)));
                    dto.setEsConfigDTO(esConfigDTO);
                }
            } else {
                updateConfigDTO(dto);
            }

        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return dto;
    }

    public synchronized void updateConfigDTO(ConfigDTO dto) {

        try {
            Node node = JcrUtil.createPath(CONFIG_NODE_PATH, JcrConstants.NT_UNSTRUCTURED, session);
            node.setProperty(GEN_ES_ADDRESS, dto.getEsAddress());
            node.setProperty(GEN_ES_PORT, dto.getEsPort());
            if (dto.getEsConfigDTO() != null) {
                Node esConfigNode = JcrUtil.createPath(CONFIG_NODE_PATH + "/" + ES_CONFIG_NODE_PATH, JcrConstants.NT_UNSTRUCTURED, session);
                ESConfigDTO esConfigDTO = dto.getEsConfigDTO();
                esConfigNode.setProperty(ES_CONFIG_CLUSTER_NAME, esConfigDTO.getClusterName());
                esConfigNode.setProperty(ES_CONFIG_CONNECT_TIMEOUT, esConfigDTO.getConnectTimeout());
                esConfigNode.setProperty(ES_CONFIG_NODES_SAMPLER_INTERVAL, esConfigDTO.getNodesSamplerInterval());
                esConfigNode.setProperty(ES_CONFIG_SNIFF, esConfigDTO.getSniff());
                esConfigNode.setProperty(ES_CONFIG_THREADPOOL_SEARCH_SIZE, esConfigDTO.getThreadpoolSearchSize());
                esConfigNode.setProperty(ES_CONFIG_THREADPOOL_SEARCH_TYPE, esConfigDTO.getThreadpoolSearchType());
                esConfigNode.setProperty(ES_CONFIG_THREADPOOL_SEARCH_QUEUE_SIZE, esConfigDTO.getThreadpoolSearchQueueSize());
                esConfigNode.setProperty(ES_CONFIG_LISTENER_PATH, generatePathValues(esConfigDTO.getListenerPath()));
                esConfigNode.setProperty(ES_CONFIG_START_LISTENER, esConfigDTO.getStartListener());
                session.save();
                configDTO = loadConfigDTO();

//                // update the elasticsearch client
//                elasticClient.updateClient();
            }

        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage(), e);
        }


    }

    public ConfigDTO getConfigDTO() {
        return configDTO;
    }

    private Value[] generatePathValues(Set<String> paths) {
        if (CollectionUtils.isNotEmpty(paths)) {
            Value[] result = new Value[paths.size()];
            return Collections2.transform(paths, new Function<String, Value>() {
                @Override
                public Value apply(String s) {
                    return new StringValue(s);
                }
            }).toArray(new Value[paths.size()]);
        } else {
            return null;
        }
    }

    private Set<String> generatePath(Property listenerPath) throws RepositoryException {
        return Sets.newHashSet(Iterables.filter(
                Collections2.transform(Arrays.asList(listenerPath.getValues()), new Function<Value, String>() {
                    @Override
                    public String apply(Value value) {
                        try {
                            return value.getString();
                        } catch (RepositoryException e) {
                            return null;
                        }
                    }
                }),
                Predicates.notNull()
        ));
    }
}
