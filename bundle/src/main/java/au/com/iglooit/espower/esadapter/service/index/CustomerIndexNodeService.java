package au.com.iglooit.espower.esadapter.service.index;

import au.com.iglooit.espower.esadapter.core.dto.FieldMappingDTO;
import au.com.iglooit.espower.esadapter.core.mapping.CustomerAssetMapping;
import au.com.iglooit.espower.esadapter.index.CustomerIndexBuilder;
import au.com.iglooit.espower.esadapter.service.CustomerFieldMappingService;
import au.com.iglooit.espower.esadapter.service.es.ElasticClient;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service(CustomerIndexNodeService.class)
@Component(metatype = true)
public class CustomerIndexNodeService {
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerIndexNodeService.class);
    @Reference
    private ElasticClient elasticClient;
    @Reference
    private CustomerFieldMappingService customerFieldMappingService;


    public void addNodeIndex(Node node, FieldMappingDTO mappingDTO) {
        try {
            elasticClient.addIndex(CustomerIndexBuilder.CUSTOMER_MAPPING_INDEX,
                    mappingDTO.getEsType(),
                    node.getIdentifier(),
                    CustomerIndexBuilder.getInstance().buildIndexByNode(node, mappingDTO));
        } catch (IOException e) {
            LOGGER.error("Can not generate index: " + e.getMessage(), e);
        } catch (RepositoryException e) {
            LOGGER.error("Can not generate index: " + e.getMessage(), e);
        }
    }


    public void saveOrUpdateNodeIndex(Node node, FieldMappingDTO mappingDTO) {
        try {
            elasticClient.addOrUpdateIndex(CustomerIndexBuilder.CUSTOMER_MAPPING_INDEX,
                    mappingDTO.getEsType(),
                    node.getIdentifier(),
                    CustomerIndexBuilder.getInstance().buildIndexByNode(node, mappingDTO));
        } catch (IOException e) {
            LOGGER.error("Can not generate index: " + e.getMessage(), e);
        } catch (RepositoryException e) {
            LOGGER.error("Can not generate index: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            LOGGER.error("Can not generate index: " + e.getMessage(), e);
        } catch (ExecutionException e) {
            LOGGER.error("Can not generate index: " + e.getMessage(), e);
        }
    }


    public void deleteNodeIndex(Node node) {
        try {
            FieldMappingDTO mappingDTO = customerFieldMappingService.getMappingBySourceType(node.getPrimaryNodeType().getName());
            if (mappingDTO != null) {
                elasticClient.deleteIndex(CustomerIndexBuilder.CUSTOMER_MAPPING_INDEX,
                        mappingDTO.getEsType(),
                        node.getIdentifier());
            } else {
                LOGGER.error("Customer index does not exist.");
            }

        } catch (RepositoryException e) {
            LOGGER.error("Can not generate index: " + e.getMessage(), e);
        }
    }


    public void updateNodeIndex(Node node, FieldMappingDTO mappingDTO) {
        try {
            elasticClient.updateIndex(CustomerIndexBuilder.CUSTOMER_MAPPING_INDEX,
                    mappingDTO.getEsType(),
                    node.getIdentifier(),
                    CustomerIndexBuilder.getInstance().buildIndexByNode(node, mappingDTO));
        } catch (RepositoryException e) {
            LOGGER.error("Can not generate index: " + e.getMessage(), e);
        }
    }

    public UpdateRequest buildIndexRequestByNode(Node node, FieldMappingDTO mapping) throws RepositoryException {
        IndexRequest indexRequest = new IndexRequest(CustomerAssetMapping.CUSTOMER_MAPPING_INDEX,
                mapping.getEsType(),
                node.getIdentifier())
                .source(CustomerIndexBuilder.getInstance().buildIndexByNode(node, mapping));
        UpdateRequest updateRequest = new UpdateRequest(CustomerAssetMapping.CUSTOMER_MAPPING_INDEX,
                mapping.getEsType(),
                node.getIdentifier())
                .doc(CustomerIndexBuilder.getInstance().buildIndexByNode(node, mapping))
                .upsert(indexRequest);

        return updateRequest;
    }
}
