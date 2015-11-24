package au.com.iglooit.espower.esadapter.service;

import au.com.iglooit.espower.esadapter.service.es.ElasticClient;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.elasticsearch.action.index.IndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
@Component
@Service(ESQueryHistoryService.class)
public class ESQueryHistoryService {
    private final Logger LOGGER = LoggerFactory.getLogger(ESQueryHistoryService.class);
    @Reference
    private ElasticClient elasticClient;

    public void addQueryHistory(String jsonContent) {
        UUID uuid = UUID.randomUUID();

        IndexResponse response = elasticClient.getClient().prepareIndex("system", "qh", uuid.toString())
                .setSource(jsonContent)
                .execute()
                .actionGet();
        LOGGER.info("Create query history: " + response.toString());
    }
}
