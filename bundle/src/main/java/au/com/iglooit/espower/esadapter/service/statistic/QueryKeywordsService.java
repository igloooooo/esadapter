package au.com.iglooit.espower.esadapter.service.statistic;

import au.com.iglooit.espower.esadapter.core.dto.statistic.KeywordsDTO;
import au.com.iglooit.espower.esadapter.service.es.ElasticClient;
import au.com.iglooit.espower.esadapter.service.ESQueryService;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicholaszhu on 30/10/2015.
 */
@Service(QueryKeywordsService.class)
@Component(metatype = true)
public class QueryKeywordsService {
    private final Logger LOGGER = LoggerFactory.getLogger(QueryKeywordsService.class);

    @Reference
    private ElasticClient elasticClient;

    public List<KeywordsDTO> top5Keywords() {
        List<KeywordsDTO> result = new ArrayList<KeywordsDTO>();
        SearchResponse response = elasticClient.getClient().prepareSearch(ESQueryService.SYSTEM_INDEX)
                .setTypes(ESQueryService.HISTORY_TYPE)
                .addAggregation(
                        AggregationBuilders.terms("field").field("query"))
                .execute().actionGet();

        return new ArrayList<KeywordsDTO>(Collections2.transform(((StringTerms) response.getAggregations().asList().get(0)).getBuckets(),
                new Function<Terms.Bucket, KeywordsDTO>() {
                    @Override
                    public KeywordsDTO apply(Terms.Bucket o) {
                        KeywordsDTO dto = new KeywordsDTO();
                        dto.setCount(o.getDocCount());
                        dto.setKeyword(o.getKey());
                        return dto;
                    }
                }));
    }
}
