package au.com.iglooit.espower.esadapter.service.index;

import au.com.iglooit.espower.esadapter.core.index.IIndexNodeService;
import au.com.iglooit.espower.esadapter.core.mapping.DefaultSystemMapping;
import au.com.iglooit.espower.esadapter.index.DefaultIndexBuilder;
import au.com.iglooit.espower.esadapter.service.es.ElasticClient;
import au.com.iglooit.espower.esadapter.util.DefaultNodeTypeUtils;
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

@Service(DefaultIndexNodeService.class)
@Component(metatype = true)
public class DefaultIndexNodeService implements IIndexNodeService {
    private final Logger LOGGER = LoggerFactory.getLogger(DefaultIndexNodeService.class);
    @Reference
    private ElasticClient elasticClient;

    @Override
    public void addNodeIndex(Node node) {
        try {
            elasticClient.addIndex(DefaultSystemMapping.DEFAULT_SYSTEM_INDEX,
                    DefaultNodeTypeUtils.getTypeNameFromNodeType(node.getPrimaryNodeType().getName()),
                    node.getIdentifier(),
                    DefaultIndexBuilder.getInstance().buildIndexByNode(node, null));
        } catch (IOException e) {
            LOGGER.error("Can not generate index: " + e.getMessage(), e);
        } catch (RepositoryException e) {
            LOGGER.error("Can not generate index: " + e.getMessage(), e);
        }
    }

    @Override
    public void saveOrUpdateNodeIndex(Node node) {
        try {
            elasticClient.addOrUpdateIndex(DefaultSystemMapping.DEFAULT_SYSTEM_INDEX,
                    DefaultNodeTypeUtils.getTypeNameFromNodeType(node.getPrimaryNodeType().getName()),
                    node.getIdentifier(),
                    DefaultIndexBuilder.getInstance().buildIndexByNode(node, null));
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

    @Override
    public void deleteNodeIndex(Node node) {
        try {
            elasticClient.deleteIndex(DefaultSystemMapping.DEFAULT_SYSTEM_INDEX,
                    DefaultNodeTypeUtils.getTypeNameFromNodeType(node.getPrimaryNodeType().getName()),
                    node.getIdentifier());
        } catch (RepositoryException e) {
            LOGGER.error("Can not generate index: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateNodeIndex(Node node) {
        try {
            elasticClient.updateIndex(DefaultSystemMapping.DEFAULT_SYSTEM_INDEX,
                    DefaultNodeTypeUtils.getTypeNameFromNodeType(node.getPrimaryNodeType().getName()),
                    node.getIdentifier(),
                    DefaultIndexBuilder.getInstance().buildIndexByNode(node, null));
        } catch (RepositoryException e) {
            LOGGER.error("Can not generate index: " + e.getMessage(), e);
        }
    }

    public UpdateRequest buildIndexRequestByNode(Node node) throws RepositoryException {
        IndexRequest indexRequest = new IndexRequest(DefaultSystemMapping.DEFAULT_SYSTEM_INDEX,
                DefaultNodeTypeUtils.getTypeNameFromNodeType(node.getPrimaryNodeType().getName()),
                node.getIdentifier())
                .source(DefaultIndexBuilder.getInstance().buildIndexByNode(node, null));
        UpdateRequest updateRequest = new UpdateRequest(DefaultSystemMapping.DEFAULT_SYSTEM_INDEX,
                DefaultNodeTypeUtils.getTypeNameFromNodeType(node.getPrimaryNodeType().getName()),
                node.getIdentifier())
                .doc(DefaultIndexBuilder.getInstance().buildIndexByNode(node, null))
                .upsert(indexRequest);

        return updateRequest;
    }
}
