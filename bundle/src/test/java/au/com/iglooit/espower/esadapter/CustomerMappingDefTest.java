package au.com.iglooit.espower.esadapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by nicholaszhu on 4/11/2015.
 */
public class CustomerMappingDefTest {
    private final Logger LOGGER = LoggerFactory.getLogger(SimpleUnitTest.class);

    private String def1 = "{\n" +
            "  \"field1\": {\n" +
            "    \"xpath\": \"asd\",\n" +
            "    \"type\":\"String\"\n" +
            "  },\n" +
            "  \"child\": {\n" +
            "    \"field2\": {\n" +
            "      \"xpath\": \"adsf\",\n" +
            "      \"type\":\"String\"\n" +
            "    },\n" +
            "    \"object2\": {\n" +
            "      \"field3\": {\n" +
            "        \"xpath\": \"as\",\n" +
            "        \"type\":\"String\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    @Test
    public void testParse() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode def = mapper.readTree(def1);
        XContentBuilder builder = jsonBuilder().startObject();
        parseDef(def, builder);
        builder.endObject();
        LOGGER.info(builder.string());
    }

    private void parseDef(JsonNode node, XContentBuilder builder) throws IOException {
        Iterator<String> fields = node.fieldNames();
        while (fields.hasNext()) {
            String name = fields.next();
            JsonNode field = node.get(name);

            if (field.has("xpath")) {
                builder.field(name, field.findPath("xpath").textValue());
            } else if (field.isContainerNode()) {
                builder.startObject(name);
                parseDef(field, builder);
                builder.endObject();
            }
        }
    }
}
