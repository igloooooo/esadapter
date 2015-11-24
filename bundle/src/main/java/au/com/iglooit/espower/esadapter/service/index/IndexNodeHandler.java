package au.com.iglooit.espower.esadapter.service.index;

import au.com.iglooit.espower.esadapter.core.dto.FieldMappingDTO;
import au.com.iglooit.espower.esadapter.core.index.IIndexNodeHandler;
import au.com.iglooit.espower.esadapter.service.CustomerFieldMappingService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

@Service(IndexNodeHandler.class)
@Component(metatype = true)
public class IndexNodeHandler implements IIndexNodeHandler {
    @Reference
    private DefaultIndexNodeService defaultIndexNodeService;
    @Reference
    private CustomerIndexNodeService customerIndexNodeService;
    @Reference
    private CustomerFieldMappingService customerFieldMappingService;


    @Override
    public void addNodeIndex(Node node) {
        try {
            // add default index
            defaultIndexNodeService.addNodeIndex(node);
            // add customer index
            FieldMappingDTO mappingDTO = customerFieldMappingService.getMappingBySourceType(node.getPrimaryNodeType().getName());
            if (mappingDTO != null) {
                customerIndexNodeService.addNodeIndex(node, mappingDTO);
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveOrUpdateNodeIndex(Node node) {
        try {
            // add default index
            defaultIndexNodeService.saveOrUpdateNodeIndex(node);
            // add customer index
            FieldMappingDTO mappingDTO = customerFieldMappingService.getMappingBySourceType(node.getPrimaryNodeType().getName());
            if (mappingDTO != null) {
                customerIndexNodeService.saveOrUpdateNodeIndex(node, mappingDTO);
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteNodeIndex(Node node) {
        try {
            // add default index
            defaultIndexNodeService.deleteNodeIndex(node);
            // add customer index
            FieldMappingDTO mappingDTO = customerFieldMappingService.getMappingBySourceType(node.getPrimaryNodeType().getName());
            if (mappingDTO != null) {
                customerIndexNodeService.deleteNodeIndex(node);
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateNodeIndex(Node node) {
        try {
            // add default index
            defaultIndexNodeService.updateNodeIndex(node);
            // add customer index
            FieldMappingDTO mappingDTO = customerFieldMappingService.getMappingBySourceType(node.getPrimaryNodeType().getName());
            if (mappingDTO != null) {
                customerIndexNodeService.updateNodeIndex(node, mappingDTO);
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
}
