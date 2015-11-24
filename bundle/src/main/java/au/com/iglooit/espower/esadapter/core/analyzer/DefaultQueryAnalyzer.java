package au.com.iglooit.espower.esadapter.core.analyzer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by nicholaszhu on 29/10/2015.
 */
public class DefaultQueryAnalyzer implements IQueryAnalyzer {
    private final Logger LOGGER = LoggerFactory.getLogger(DefaultQueryAnalyzer.class);
    private static final String QUERY = "query";
//    private static final String
    @Override
    public String getKeywords(String queryContent) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode actualObj = mapper.readTree(queryContent);

        } catch (IOException e) {
            LOGGER.error("Can not parse query content.", e);

        }
        return "";
    }
}
