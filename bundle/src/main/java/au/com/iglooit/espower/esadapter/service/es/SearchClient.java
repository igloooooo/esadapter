package au.com.iglooit.espower.esadapter.service.es;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;

@Component
@Service(SearchClient.class)
public class SearchClient {
    @Reference
    private ElasticClient elasticClient;
    public SearchResponse search() {
//        return elasticClient.getClient().prepareSearch("aem", "index2")
//                .setTypes("type1", "type2")
//                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                .setQuery(QueryBuilders.termQuery("multi", "test"))             // Query
//                .setPostFilter(FilterBuilders.rangeFilter("age").from(12).to(18))   // Filter
//                .setFrom(0).setSize(60).setExplain(true)
//                .execute()
//                .actionGet();

        return elasticClient.getClient().prepareSearch("aem")
                .setTypes("dam_Asset", "dam_AssetContent")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery("name", "pdf"))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();
    }

    public SearchResponse search(String content) {
        return elasticClient.getClient().prepareSearch("aem")
                .setTypes("dam_Asset", "dam_AssetContent")
//                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(content)
//                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();
    }
}
