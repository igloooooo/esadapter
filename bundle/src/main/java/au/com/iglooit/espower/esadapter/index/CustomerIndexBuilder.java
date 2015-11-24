package au.com.iglooit.espower.esadapter.index;

import au.com.iglooit.espower.esadapter.core.dto.CustomerFieldMapItemDTO;
import au.com.iglooit.espower.esadapter.core.dto.FieldMappingDTO;
import au.com.iglooit.espower.esadapter.core.ex.ESIndexBuilderX;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import java.io.IOException;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by nicholaszhu on 7/11/2015.
 */
public class CustomerIndexBuilder implements IndexBuilder {
    public final static String CUSTOMER_MAPPING_INDEX = "aem_customer";
    private final Logger LOGGER = LoggerFactory.getLogger(DefaultIndexBuilder.class);
    private static CustomerIndexBuilder indexBuilder;

    private CustomerIndexBuilder() {

    }

    public static CustomerIndexBuilder getInstance() {
        if (indexBuilder == null) {
            return new CustomerIndexBuilder();
        }
        return indexBuilder;
    }

    @Override
    public String buildIndexByNode(Node node, FieldMappingDTO mappingDTO) {
        try {
            XContentBuilder builder = jsonBuilder().startObject();
            parseDef(node, mappingDTO.getChildren(), builder);
            builder.endObject();
            return builder.string();
        } catch (IOException e) {
            throw new ESIndexBuilderX("Can not generate Index data", e);
        }
    }

    private void parseDef(Node source, List<CustomerFieldMapItemDTO> defList, XContentBuilder builder) throws IOException {

        for (CustomerFieldMapItemDTO defDto : defList) {

            if (StringUtils.isNotBlank(defDto.getXpath())) {
                try {
                    builder.field(defDto.getName(),
                            parseProperty(source,
                                    defDto.getXpath(),
                                    defDto.getType()));
                } catch (RepositoryException e) {
                    LOGGER.warn("Can not parse field: " + defDto.getName());
                }
            }
            if (CollectionUtils.isNotEmpty(defDto.getChildren())) {
                builder.startObject("children");
                parseDef(source, defDto.getChildren(), builder);
                builder.endObject();
            }
        }
    }

    private Object parseProperty(Node source, String relPath, Integer type) throws RepositoryException {
        Property property = source.getProperty(relPath);
        switch (type) {
            case PropertyType.STRING:
                return property.getString();
            case PropertyType.DATE:
                return property.getDate();
            case PropertyType.LONG:
                return property.getLong();
            case PropertyType.DOUBLE:
                return property.getDouble();
            case PropertyType.DECIMAL:
                return property.getDecimal();
            default:
                return property.getValue();
        }
    }
}
