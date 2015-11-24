package au.com.iglooit.espower.esadapter.index;

import au.com.iglooit.espower.esadapter.core.dto.FieldMappingDTO;
import au.com.iglooit.espower.esadapter.mapper.DefaultAEMMapper;
import au.com.iglooit.espower.esadapter.util.DefaultNodeTypeUtils;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * This builder only works on the cq:page, dam:asset
 */
public class DefaultIndexBuilder implements IndexBuilder {
    private final Logger LOGGER = LoggerFactory.getLogger(DefaultIndexBuilder.class);

    private static DefaultIndexBuilder indexBuilder;

    private DefaultIndexBuilder() {

    }

    public static DefaultIndexBuilder getInstance() {
        if (indexBuilder == null) {
            return new DefaultIndexBuilder();
        }
        return indexBuilder;
    }

    @Override
    public String buildIndexByNode(Node node, FieldMappingDTO mappingDTO) {
        try {
            String id = node.getIdentifier();

            XContentBuilder builder = jsonBuilder().startObject();
            // common part
            builder.field("id", id)
                    .field("nodeType", node.getPrimaryNodeType().getName())
                    .field("path", node.getPath())
                    .field("name", node.getName());
            extractProperties(node.getProperties(), builder);
            // check node type
            // if page
            if (DefaultNodeTypeUtils.isPage(node.getPrimaryNodeType().getName())) {
                extractPage(node, builder);
            } else if (DefaultNodeTypeUtils.isDam(node.getPrimaryNodeType().getName())) {
                extractDam(node, builder);
            } else {
                LOGGER.debug("Ignore the node type: " + node.getPrimaryNodeType().getName());
            }

            builder.endObject();
            return builder.string();
        } catch (RepositoryException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    private void extractDam(Node node, XContentBuilder builder) throws RepositoryException, IOException {
        Node damContent = node.getNode("jcr:content");
        if (damContent != null) {
            Node metaData = damContent.getNode("metadata");
            if (metaData != null) {
                builder.startObject("content");
                extractProperties(metaData.getProperties(), builder);
                builder.endObject();
            }
            if (damContent.hasNode("renditions/original/jcr:content")) {
                Node originalData = damContent.getNode("renditions/original/jcr:content");
                builder.field("file", extractDamOriginalData(originalData));
            }
        }

    }

    private void extractPage(Node node, XContentBuilder builder) throws RepositoryException, IOException {
        Node pageContent = node.getNode("jcr:content");
        if (pageContent != null) {
            builder.startObject("content");
            extractProperties(pageContent.getProperties(), builder);
            // extract all components property
            extractPageComponents(pageContent.getNodes(), builder);
            builder.endObject();
        }

    }

    private void extractProperties(PropertyIterator iterator, XContentBuilder builder) throws RepositoryException, IOException {
        DefaultAEMMapper mapper = DefaultAEMMapper.getInstance();
        while (iterator.hasNext()) {
            Property property = iterator.nextProperty();
            if (mapper.isIgnore(property.getName())) {
                continue;
            }
            if (property.isMultiple()) {
                builder.field(property.getName(), Collections2.transform(Arrays.asList(property.getValues()), new Function<Value, String>() {
                    @Override
                    public String apply(Value o) {
                        try {
                            return o.getString();
                        } catch (RepositoryException e) {
                            LOGGER.error(e.getMessage());
                            return "";
                        }
                    }
                }));
            } else if (property.getType() == PropertyType.STRING) {
                builder.field(property.getName(), property.getString());
            } else if (property.getType() == PropertyType.DATE) {
                builder.field(property.getName(), property.getDate());
            } else if (property.getType() == PropertyType.DOUBLE) {
                builder.field(property.getName(), property.getDouble());
            } else if (property.getType() == PropertyType.LONG) {
                builder.field(property.getName(), property.getLong());
            } else if (property.getType() == PropertyType.DECIMAL) {
                builder.field(property.getName(), property.getDecimal());
            } else if (property.getType() == PropertyType.REFERENCE) {

                builder.field(property.getName(), Collections2.transform(Arrays.asList(property.getValues()), new Function<Value, String>() {
                    @Override
                    public String apply(Value o) {
                        try {
                            return o.getString();
                        } catch (RepositoryException e) {
                            LOGGER.error(e.getMessage());
                            return "";
                        }
                    }
                }));
            }

        }
    }

    private void extractPageComponents(NodeIterator nodeIterator, XContentBuilder builder) throws IOException, RepositoryException {
        if (nodeIterator != null) {
            while (nodeIterator.hasNext()) {
                Node child = nodeIterator.nextNode();
                builder.startObject(child.getName());
                extractProperties(child.getProperties(), builder);
                extractPageComponents(child.getNodes(), builder);
                builder.endObject();
            }
        }

    }

    /**
     * @param originalData
     * @return base64 content
     * @throws RepositoryException
     */
    private String extractDamOriginalData(Node originalData) throws RepositoryException {
        if (originalData != null) {
            if (originalData.hasProperty("jcr:data")) {
                byte[] result = null;
                InputStream is = null;
                BufferedInputStream bin = null;
                try {
                    is = originalData.getProperty("jcr:data").getBinary().getStream();
                    bin = new BufferedInputStream(is);
                    result = IOUtils.toByteArray(bin);
                } catch (Exception ex) {
                    LOGGER.error("Can not ready data from dam:Asset.", ex);
                } finally {
                    try {
                        if (bin != null) {
                            bin.close();
                        }
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        LOGGER.error("Can not close IO. ", e);
                    }
                }
                if (result != null) {
                    return DatatypeConverter.printBase64Binary(result);
                }
                return "";
            }
        }
        return "";
    }
}
