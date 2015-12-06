package au.com.iglooit.espower.esadapter.service;

import au.com.iglooit.espower.esadapter.core.dto.CustomerFieldMapItemDTO;
import au.com.iglooit.espower.esadapter.core.dto.FieldMappingDTO;
import au.com.iglooit.espower.esadapter.core.mapping.CustomerAssetMapping;
import au.com.iglooit.espower.esadapter.util.DateUtil;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.commons.jcr.JcrUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.util.*;

/**
 * Created by nicholaszhu on 4/11/2015.
 */
@Service(CustomerFieldMappingService.class)
@Component(metatype = true)
public class CustomerFieldMappingService implements CustomerAssetMapping {
    public static final String CQ_TYPE = "cqType";
    public static final String ES_TYPE = "esType";
    public static final String MAPPING = "mapping";
    public static final String LAST_UPDATE = "jcr:lastModified";
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerFieldMappingService.class);
    private final String MAPPING_PATH = "/etc/esadaptermap/mapping";

    private Map<String, FieldMappingDTO> cachedFieldMappingList = new HashMap<String, FieldMappingDTO>();

    @Reference
    private SlingRepository repository;
    private Session session;

    protected void activate(ComponentContext context) throws Exception {
        session = repository.loginAdministrative(null);
        updateFieldMapping(getFieldMappingList());
        LOGGER.info("Init customer Field Mapping.");
    }


    @Override
    public boolean ignoreNode(String cqNodeType) {
        return getType(cqNodeType) == null ? true : false;
    }

    @Override
    public String getType(String cqNodeName) {
        if (cachedFieldMappingList.containsKey(cqNodeName)) {
            return cachedFieldMappingList.get(cqNodeName).getEsType();
        }
        return null;
    }

    @Override
    public FieldMappingDTO getMapping(String type) {
        try {
            Node node = JcrUtil.createPath(MAPPING_PATH + "/" + type, JcrConstants.NT_UNSTRUCTURED, session);
            if (node != null) {
                return parseMapNode(node);
            }
            return null;
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }

    }

    @Override
    public FieldMappingDTO getMappingBySourceType(String cqType) {
        if (cachedFieldMappingList.containsKey(cqType)) {
            return cachedFieldMappingList.get(cqType);
        }
        return null;
    }

    @Override
    public void saveOrUpdateMapping(String cqType, String type, String json) {

        try {
            Node node = JcrUtil.createPath(MAPPING_PATH + "/" + type, JcrConstants.NT_UNSTRUCTURED, session);
            node.setProperty(CQ_TYPE, cqType);
            node.setProperty(ES_TYPE, type);
            node.setProperty(MAPPING, json);
            node.setProperty(LAST_UPDATE, Calendar.getInstance());
            updateFieldMapping(getFieldMappingList());
            session.save();
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    @Override
    public List<FieldMappingDTO> getFieldMappingList() {
        List<FieldMappingDTO> result = new ArrayList<FieldMappingDTO>();
        try {
            Node node = JcrUtil.createPath(MAPPING_PATH, JcrConstants.NT_UNSTRUCTURED, session);
            NodeIterator iterator = node.getNodes();
            while (iterator.hasNext()) {
                Node item = iterator.nextNode();
                result.add(parseMapNode(item));
            }
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return result;
    }

    private FieldMappingDTO parseMapNode(Node node) throws RepositoryException {
        FieldMappingDTO dto = new FieldMappingDTO();
        dto.setCqType(node.getProperty(CQ_TYPE).getString());
        dto.setEsType(node.getProperty(ES_TYPE).getString());
        dto.setIndex(CUSTOMER_MAPPING_INDEX);
        ObjectMapper mapper = new ObjectMapper();
        try {
            dto.setChildren((List<CustomerFieldMapItemDTO>)mapper.readValue(node.getProperty(MAPPING).getString(),
                    new TypeReference<List<CustomerFieldMapItemDTO>>(){}));
        } catch (IOException e) {
            LOGGER.error("Can not parse mapping content.", e);
        }
        dto.setLastModified(DateUtil.print(node.getProperty(LAST_UPDATE).getDate().getTime()));
        return dto;

    }

    private synchronized void updateFieldMapping(List<FieldMappingDTO> mapping) {
        for (FieldMappingDTO dto : mapping) {
            cachedFieldMappingList.put(dto.getCqType(), dto);
        }
    }

}
