package au.com.iglooit.espower.esadapter.service.statistic;

import au.com.iglooit.espower.esadapter.core.dto.statistic.SlowQueryDTO;
import au.com.iglooit.espower.esadapter.core.index.SystemIndexConst;
import au.com.iglooit.espower.esadapter.service.es.ElasticClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nicholaszhu on 31/10/2015.
 */
@Service(SlowQueryService.class)
@Component(metatype = true)
public class SlowQueryService {
    private final Logger LOGGER = LoggerFactory.getLogger(SlowQueryService.class);
    @Reference
    private ElasticClient elasticClient;

    public List<SlowQueryDTO> top5SlowQuery() {

        SearchResponse response = elasticClient.getClient().prepareSearch(SystemIndexConst.SYSTEM_INDEX)
                .setTypes(SystemIndexConst.HISTORY_TYPE)
                .setSize(5).addSort("took", SortOrder.DESC).execute().actionGet();
        return new ArrayList<SlowQueryDTO>(Collections2.transform(Arrays.asList(response.getHits().getHits()),
                new Function<SearchHit, SlowQueryDTO>() {
            @Override
            public SlowQueryDTO apply(SearchHit o) {
                SlowQueryDTO dto = new SlowQueryDTO();
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    JsonNode rootNode = objectMapper.readTree(o.getSourceAsString());
                    dto.setContent(rootNode.get("searchContent").toString());
                    dto.setTook(rootNode.get("took").asLong());
                } catch (IOException e) {
                    LOGGER.error("Can not parse query history result.", e);
                }
                return dto;
            }
        }));
    }

    public long slowestQuery() {
        SearchResponse response = elasticClient.getClient().prepareSearch(SystemIndexConst.SYSTEM_INDEX)
                .setTypes(SystemIndexConst.HISTORY_TYPE)
                .setSize(1).addSort("took", SortOrder.DESC).execute().actionGet();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(response.getHits().getAt(0).getSourceAsString());

            return rootNode.get("took").asLong();
        } catch (IOException e) {
            LOGGER.error("Can not parse query history result.", e);
            return 0L;
        }
    }

    public double averageQueryTime() {
        SearchResponse response = elasticClient.getClient().prepareSearch(SystemIndexConst.SYSTEM_INDEX)
                .setTypes(SystemIndexConst.HISTORY_TYPE)
                .addAggregation(
                        AggregationBuilders.avg("avg_took").field("took"))
                .execute().actionGet();

        return ((InternalAvg) response.getAggregations().get("avg_took")).value();

    }
}
