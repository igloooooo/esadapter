package au.com.iglooit.espower.esadapter.core.mapping;

import au.com.iglooit.espower.esadapter.core.dto.FieldMappingDTO;
import au.com.iglooit.espower.esadapter.core.dto.MappingDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by nicholaszhu on 3/11/2015.
 */
public interface CustomerAssetMapping {
    public String REL_PATH_NAME = "xpath";
    public String TYPE_NAME = "type";
    public String CUSTOMER_MAPPING_INDEX = "aem_customer";
    /**
     * 1. check node type ignore or not
     * 2. get mapping from node type -> compare the field property
     * 3. if there is no mapping, generate automatically.
     * 4. add a new mapping
     * 5. update a mapping
     */
    public boolean ignoreNode(String cqNodeType);

    /**
     *
     * @param cqNodeName node type of cq.
     * @return ES type name
     */
    public String getType(String cqNodeName);

    /**
     *
     * @param type index type
     * @return field map, key is cq property, value is es mapping field name
     */
    public FieldMappingDTO getMapping(String type);

    /**
     *
     * @param cqType e.x. cq:Page
     * @return
     */
    public FieldMappingDTO getMappingBySourceType(String cqType);

    /**
     *
     * @param cqType cq type
     * @param type es type
     * @param json
     */
    public void saveOrUpdateMapping(String cqType, String type, String json);

    /**
     * get the list of field mapping
     * @return
     */
    public List<FieldMappingDTO> getFieldMappingList();
}
