package au.com.iglooit.espower.esadapter.service;

import au.com.iglooit.espower.esadapter.core.dto.MappingDTO;
import au.com.iglooit.espower.esadapter.core.mapping.DefaultSystemMapping;
import au.com.iglooit.espower.esadapter.service.es.ElasticClient;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.hppc.procedures.ObjectProcedure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicholaszhu on 2/11/2015.
 */
@Component(metatype = true)
@Service(ESMappingService.class)
public class ESMappingService {
    private final Logger LOGGER = LoggerFactory.getLogger(ESMappingService.class);

    @Reference
    private ElasticClient elasticClient;

    public List<MappingDTO> getMappings(String index) {
        final List<MappingDTO> result = new ArrayList<MappingDTO>();
        GetMappingsResponse response = elasticClient.getClient().admin().indices().prepareGetMappings(index).execute().actionGet();
        ImmutableOpenMap map = response.getMappings().get(DefaultSystemMapping.DEFAULT_SYSTEM_INDEX);
        final DefaultSystemMapping mapping = DefaultSystemMapping.getInstance();
        map.values().forEach(new ObjectProcedure() {
            @Override
            public void apply(Object o) {
                MappingDTO dto = new MappingDTO();
                dto.setIndex(DefaultSystemMapping.DEFAULT_SYSTEM_INDEX);
                dto.setType(((MappingMetaData) o).type());
                dto.setCqType(mapping.getAEMAssetName(dto.getType()));
                try {
                    dto.setMappingContent(((MappingMetaData) o).source().string());
                } catch (IOException e) {
                    LOGGER.warn("Can not parse mapping content");
                }
                result.add(dto);
            }
        });
        return result;
    }

    public MappingDTO getMapping(String index, String type) {
        final DefaultSystemMapping mapping = DefaultSystemMapping.getInstance();
        GetMappingsResponse response = elasticClient.getClient().admin().indices()
                .prepareGetMappings(index).setTypes(type)
                .execute().actionGet();
        MappingMetaData metaData = response.getMappings().get(DefaultSystemMapping.DEFAULT_SYSTEM_INDEX).get(type);
        MappingDTO dto = new MappingDTO();
        dto.setIndex(DefaultSystemMapping.DEFAULT_SYSTEM_INDEX);
        dto.setType(metaData.type());
        dto.setCqType(mapping.getAEMAssetName(dto.getType()));
        try {
            dto.setMappingContent(metaData.source().string());
        } catch (IOException e) {
            LOGGER.warn("Can not parse mapping content");
        }
        return dto;
    }
}
