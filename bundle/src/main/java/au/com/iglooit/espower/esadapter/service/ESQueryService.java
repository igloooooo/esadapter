package au.com.iglooit.espower.esadapter.service;

import au.com.iglooit.espower.esadapter.core.mapping.DefaultSystemMapping;
import au.com.iglooit.espower.esadapter.core.searchhistory.SearchHistory;
import au.com.iglooit.espower.esadapter.service.es.ElasticClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Calendar;

@Component(metatype = true)
@Service(ESQueryService.class)
public class ESQueryService {

    public final static String DAM_TYPE = "dam_Asset";
    public final static String PAGE_TYPE = "page";


    private final Logger LOGGER = LoggerFactory.getLogger(ESQueryService.class);
    @Reference
    private ESQueryHistoryService esQueryHistoryService;
    @Reference
    private ElasticClient elasticClient;

    public SearchResponse search(String content, String index, String[] types, int from, int size) {
        LOGGER.info("ES Query Content: " + content);
        SearchRequestBuilder builder = elasticClient.getClient().prepareSearch(index)
                .setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(content)
                .setFrom(from).setSize(size).setExplain(true);

        SearchResponse response = builder.execute()
                .actionGet();
        try {
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setHit(response.getHits().getTotalHits());
            ObjectMapper mapper = new ObjectMapper();
            searchHistory.setSearchContent(mapper.readTree(content));
            searchHistory.setTook(response.getTookInMillis());
            searchHistory.setMaxScore(response.getHits().getMaxScore());
            searchHistory.setTimestamp(Calendar.getInstance().getTime());
            esQueryHistoryService.addQueryHistory(mapper.writeValueAsString(searchHistory));
        } catch (JsonProcessingException e) {
            LOGGER.error("Can not record the search history.", e);
        } catch (IOException e) {
            LOGGER.error("Can not record the search history.", e);
        }
        return response;
    }

    public SearchResponse search(String content, int from, int size) {
        return search(content, DefaultSystemMapping.DEFAULT_SYSTEM_INDEX,  new String[] {DAM_TYPE}, from, size);
    }

    public SearchResponse search(String content) {
        return search(content, 0, 60);
    }
}
