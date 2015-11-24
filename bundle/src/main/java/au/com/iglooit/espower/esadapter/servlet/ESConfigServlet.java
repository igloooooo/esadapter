package au.com.iglooit.espower.esadapter.servlet;

import au.com.iglooit.espower.esadapter.core.dto.ConfigDTO;
import au.com.iglooit.espower.esadapter.service.ESAdapterConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;

@Component(metatype = false)
@Service(javax.servlet.Servlet.class)
@Properties({
        @Property(name = "sling.servlet.paths", value = "/bin/esadapter/config", label = "ES Adapter - Configuration", description = "ES Adapter - Configuration"),
        @Property(name = "service.description", value = "ES Adapter - Configuration"),
        @Property(name = "sling.auth.requirements", value = "-/bin/esadapter/config"),
        @Property(name = "label", value = "ES Adapter - Configuration")})
public class ESConfigServlet extends SlingAllMethodsServlet {
    private final Logger LOGGER = LoggerFactory.getLogger(ESConfigServlet.class);
    @Reference
    private ESAdapterConfigService esAdapterConfigService;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("update the ES Adapter configuration");
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        ObjectMapper mapper = new ObjectMapper();
        ConfigDTO dto = mapper.readValue(sb.toString(), ConfigDTO.class);
        esAdapterConfigService.updateConfigDTO(dto);
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Get the ES Adapter configuration");
        ConfigDTO dto = esAdapterConfigService.getConfigDTO();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), dto);
    }
}
