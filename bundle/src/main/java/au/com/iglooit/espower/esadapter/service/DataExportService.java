package au.com.iglooit.espower.esadapter.service;

import au.com.iglooit.espower.esadapter.core.dto.DataExportResponse;
import au.com.iglooit.espower.esadapter.core.dto.FieldMappingDTO;
import au.com.iglooit.espower.esadapter.service.es.ElasticClient;
import au.com.iglooit.espower.esadapter.service.index.CustomerIndexNodeService;
import au.com.iglooit.espower.esadapter.service.index.DefaultIndexNodeService;
import au.com.iglooit.espower.esadapter.util.DefaultNodeTypeUtils;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicholaszhu on 24/10/2015.
 */
@Service(DataExportService.class)
@Component(metatype = true)
public class DataExportService {
    private final Logger LOGGER = LoggerFactory.getLogger(DataExportService.class);
    @Reference
    private ElasticClient elasticClient;
    @Reference
    private SlingRepository repository;
    @Reference
    private CustomerFieldMappingService customerFieldMappingService;
    @Reference
    private CustomerIndexNodeService customerIndexNodeService;
    @Reference
    private DefaultIndexNodeService defaultIndexNodeService;


    private Session session;

    protected void activate(ComponentContext context) throws Exception {
        session = repository.loginAdministrative(null);

        LOGGER.info("DataExportService start up.");
    }

    public DataExportResponse exportData(String parentPath, Boolean dryRun) {
        List<Node> indexNodeList = new ArrayList<Node>();
        try {
            Node node = session.getNode(parentPath);
            if (node != null) {
                generateNodeList(node, indexNodeList);
            }
            if (!dryRun) {
                bulkProcess(indexNodeList);
            }
            DataExportResponse response = new DataExportResponse();
            response.setCount(Long.valueOf(indexNodeList.size()));
            response.setExportedPathList(new ArrayList<String>(Collections2.transform(indexNodeList, new Function<Node, String>() {
                @Override
                public String apply(Node node) {
                    try {
                        return node.getPath();
                    } catch (RepositoryException e) {
                        LOGGER.debug("Can not get the path for node " + node);
                        return "";
                    }
                }
            })));
            return response;
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
        }
        return null;

    }

    private void generateNodeList(Node parent, List<Node> result) throws RepositoryException {
        String type = parent.getPrimaryNodeType().getName();
        if (!DefaultNodeTypeUtils.isIgnoreNode(parent.getPrimaryNodeType().getName())
                || !customerFieldMappingService.ignoreNode(type)) {
            result.add(parent);
        }
        if (DefaultNodeTypeUtils.isFolder(type) || DefaultNodeTypeUtils.isPage(type) || DefaultNodeTypeUtils.isDam(type)) {
            // folder, skip
            NodeIterator iterator = parent.getNodes();
            while (iterator.hasNext()) {
                generateNodeList(iterator.nextNode(), result);
            }
        }
    }

    private DataExportResponse bulkProcess(List<Node> pathList) {
        final DataExportResponse dataExportResponse = new DataExportResponse();
        BulkProcessor bulkProcessor = BulkProcessor.builder(elasticClient.getClient(), new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                LOGGER.info("Bulk process start at " + executionId);
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                LOGGER.info("Bulk process finished at " + executionId);
                dataExportResponse.setCount(Long.valueOf(response.getItems().length));
                dataExportResponse.setTimeTook(response.getTookInMillis());
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                LOGGER.error("There are some errors on the bulk process: " + failure.getMessage());

            }
        }).setBulkActions(10000)
                .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.GB))
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(1)
                .build();
        for (Node node : pathList) {
            try {
                // build system default index
                bulkProcessor.add(defaultIndexNodeService.buildIndexRequestByNode(node));
                // build customer index;
                FieldMappingDTO customerMapping = customerFieldMappingService.getMappingBySourceType(node.getPrimaryNodeType().getName());
                if (customerMapping != null) {
                    bulkProcessor.add(customerIndexNodeService.buildIndexRequestByNode(node, customerMapping));
                }

            } catch (RepositoryException e) {
                LOGGER.warn("Can not generate index for node: " + node);
            }
        }


        return dataExportResponse;
    }
}
