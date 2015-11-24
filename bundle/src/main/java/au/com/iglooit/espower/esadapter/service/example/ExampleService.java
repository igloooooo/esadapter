package au.com.iglooit.espower.esadapter.service.example;

import au.com.iglooit.espower.esadapter.service.es.ElasticClient;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Showing the search example.
 */
@Service(ExampleService.class)
@Component(metatype = true)
public class ExampleService {
    private final Logger LOGGER = LoggerFactory.getLogger(ExampleService.class);
    @Reference
    private ElasticClient elasticClient;


    public SearchResponse query(String content, List<String> denyPathList) {

        List<FilterBuilder> termFilters = new ArrayList<FilterBuilder>();
        for(String denyPath : denyPathList) {
            termFilters.add(FilterBuilders.termFilter("path", denyPath));
        }

        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery(content);
        BaseFilterBuilder filterBuilder = FilterBuilders.notFilter(FilterBuilders.orFilter(termFilters.toArray(new FilterBuilder[termFilters.size()])));
        SearchRequestBuilder builder = elasticClient.getClient().prepareSearch("aem")
                .setTypes("cq_Page")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.filteredQuery(queryBuilder, filterBuilder))
                .setFrom(0).setSize(10);
        return builder.execute()
                .actionGet();
    }

    public SearchResponse queryAttachment(String content) {
        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery(content);
        SearchRequestBuilder builder = elasticClient.getClient().prepareSearch("aem")
                .setTypes("dam_Asset")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(queryBuilder)
                .addHighlightedField("file")
                .setFrom(0).setSize(10);
        return builder.execute()
                .actionGet();
    }
}
