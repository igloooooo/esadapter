package au.com.iglooit.espower.esadapter.service.statistic;

import au.com.iglooit.espower.esadapter.core.dto.statistic.StatisticBoardDTO;
import au.com.iglooit.espower.esadapter.core.index.SystemIndexConst;
import au.com.iglooit.espower.esadapter.core.mapping.CustomerAssetMapping;
import au.com.iglooit.espower.esadapter.core.mapping.DefaultSystemMapping;
import au.com.iglooit.espower.esadapter.index.IndexBuilder;
import au.com.iglooit.espower.esadapter.service.es.ElasticClient;
import au.com.iglooit.espower.esadapter.util.DateUtil;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * Created by nicholaszhu on 26/11/2015.
 */
@Service(StatisticBoardService.class)
@Component(metatype = true)
public class StatisticBoardService {
    @Reference
    private ElasticClient elasticClient;
    @Reference
    private SlowQueryService slowQueryService;

    public StatisticBoardDTO generateDashboardData() {
        StatisticBoardDTO dto = new StatisticBoardDTO();
        CountResponse customerResponse = elasticClient.getClient().prepareCount(CustomerAssetMapping
                .CUSTOMER_MAPPING_INDEX)
                .execute()
                .actionGet();

        CountResponse aemResponse = elasticClient.getClient().prepareCount(DefaultSystemMapping.DEFAULT_SYSTEM_INDEX)
                .execute()
                .actionGet();
        dto.setNodeCount(customerResponse.getCount() + aemResponse.getCount());
        CountResponse queryResponse = elasticClient.getClient().prepareCount(SystemIndexConst.SYSTEM_INDEX)
                .setQuery(termQuery("_type", SystemIndexConst.HISTORY_TYPE))
                .execute()
                .actionGet();
        dto.setQueryCount(queryResponse.getCount());

        GetMappingsResponse systemMapResponse = elasticClient.getClient().admin().indices().prepareGetMappings
                (DefaultSystemMapping.DEFAULT_SYSTEM_INDEX).execute().actionGet();
        GetMappingsResponse customerMapResponse = elasticClient.getClient().admin().indices().prepareGetMappings
                (CustomerAssetMapping.CUSTOMER_MAPPING_INDEX).execute().actionGet();
        dto.setIndexCount(Long.valueOf(systemMapResponse.getMappings().size() + customerMapResponse.getMappings().size()));

        // slowest query
        dto.setSlowestQuery(slowQueryService.slowestQuery());
        // average query
        dto.setAverageQuery(slowQueryService.averageQueryTime());
        // latest index
        dto.setThisWeekIndex(getLastWeekIndex());
        dto.setYesterdayIndex(getYesterdayIndex());

        return dto;
    }

    private Long getLastWeekIndex() {
        QueryBuilder lastWeekQuery = QueryBuilders.rangeQuery(IndexBuilder.CREATION_DATE)
                .from(DateUtil.beginOfThisWeek().getTime())
                .to(DateUtil.endOfThisWeek().getTime());
        CountResponse aemResponse = elasticClient.getClient().prepareCount(DefaultSystemMapping.DEFAULT_SYSTEM_INDEX)
                .setQuery(lastWeekQuery)
                .execute()
                .actionGet();
        CountResponse customerResponse = elasticClient.getClient().prepareCount(CustomerAssetMapping
                .CUSTOMER_MAPPING_INDEX)
                .setQuery(lastWeekQuery)
                .execute()
                .actionGet();
        return aemResponse.getCount() + customerResponse.getCount();
    }

    private Long getYesterdayIndex() {
        QueryBuilder lastWeekQuery = QueryBuilders.rangeQuery(IndexBuilder.CREATION_DATE)
                .from(DateUtil.beginOfYesterday().getTime())
                .to(DateUtil.endOfYesterday().getTime());
        CountResponse aemResponse = elasticClient.getClient().prepareCount(DefaultSystemMapping.DEFAULT_SYSTEM_INDEX)
                .setQuery(lastWeekQuery)
                .execute()
                .actionGet();
        CountResponse customerResponse = elasticClient.getClient().prepareCount(CustomerAssetMapping
                .CUSTOMER_MAPPING_INDEX)
                .setQuery(lastWeekQuery)
                .execute()
                .actionGet();
        return aemResponse.getCount() + customerResponse.getCount();
    }


}
