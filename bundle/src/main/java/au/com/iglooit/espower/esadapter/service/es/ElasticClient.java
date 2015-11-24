package au.com.iglooit.espower.esadapter.service.es;

import au.com.iglooit.espower.esadapter.core.dto.ConfigDTO;
import au.com.iglooit.espower.esadapter.core.mapping.DefaultSystemMapping;
import au.com.iglooit.espower.esadapter.impl.ESTransportClient;
import au.com.iglooit.espower.esadapter.index.DefaultIndexBuilder;
import au.com.iglooit.espower.esadapter.service.ESAdapterConfigService;
import au.com.iglooit.espower.esadapter.util.DefaultNodeTypeUtils;
import org.apache.felix.scr.annotations.*;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
@Service(ElasticClient.class)
public class ElasticClient {
    private final Logger LOGGER = LoggerFactory.getLogger(ElasticClient.class);
    private TransportClient client;

    @Reference
    private ESAdapterConfigService esAdapterConfigService;

    @Activate
    protected void activate(ComponentContext context) throws Exception {
        updateClient();
    }

    public synchronized void updateClient() {
        ConfigDTO configDTO = esAdapterConfigService.getConfigDTO();
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", configDTO.getEsConfigDTO().getClusterName()).
                        put("network.tcp.connect_timeout", configDTO.getEsConfigDTO().getConnectTimeout())
                .put("client.transport.nodes_sampler_interval", configDTO.getEsConfigDTO().getNodesSamplerInterval())
                .put("client.transport.sniff", configDTO.getEsConfigDTO().getSniff())
                .put("threadpool.search.type", configDTO.getEsConfigDTO().getThreadpoolSearchType())
                .put("threadpool.search.size", configDTO.getEsConfigDTO().getThreadpoolSearchSize())
                .put("threadpool.search.queue_size", configDTO.getEsConfigDTO().getThreadpoolSearchQueueSize())
                .build();
        client = new ESTransportClient(settings).addTransportAddress(
                new InetSocketTransportAddress(configDTO.getEsAddress(),
                        configDTO.getEsPort()));
    }

    protected void deactivate(ComponentContext componentContext) {
        client.close();
    }

    public void addIndex(String index, String type, String id, String content) throws IOException {
        IndexResponse response = client.prepareIndex(index, type, id)
                .setSource(content)
                .execute()
                .actionGet();

    }

    public void addOrUpdateIndex(String index, String type, String id, String content) throws IOException, ExecutionException, InterruptedException {
        IndexRequest indexRequest = new IndexRequest(index, type, id)
                .source(content);
        UpdateRequest updateRequest = new UpdateRequest(index, type, id)
                .doc(content)
                .upsert(indexRequest);

        client.update(updateRequest).get();
    }

    public void updateIndex(String index, String type, String id, String content) {
        UpdateRequestBuilder builder = client.prepareUpdate(index,
                type, id)
                .setDoc(content);
        LOGGER.debug(builder.toString());
        UpdateResponse response = builder.execute().actionGet();
        LOGGER.debug(response.toString());
    }

    public void deleteIndex(String index, String type, String id) {
        LOGGER.info("Delete the default index for node: " + id);
        // delete default index data
        DeleteResponse response = client.prepareDelete(index, type, id)
                .execute()
                .actionGet();
    }

    public TransportClient getClient() {
        return client;
    }
}
