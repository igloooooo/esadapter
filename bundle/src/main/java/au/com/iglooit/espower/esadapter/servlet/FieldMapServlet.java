package au.com.iglooit.espower.esadapter.servlet;

import au.com.iglooit.espower.esadapter.core.dto.FieldMappingDTO;
import au.com.iglooit.espower.esadapter.core.mapping.CustomerAssetMapping;
import au.com.iglooit.espower.esadapter.service.CustomerFieldMappingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Component(metatype = false)
@Service(javax.servlet.Servlet.class)
@Properties({
        @Property(name = "sling.servlet.paths", value = "/bin/esadapter/fieldmapping", label = "ES Adapter - Field Mapping", description = "ES Adapter - Field Mapping"),
        @Property(name = "service.description", value = "ES Adapter - Field Mapping"),
        @Property(name = "sling.auth.requirements", value = "-/bin/esadapter/fieldmapping"),
        @Property(name = "label", value = "ES Adapter - Field Mapping")})
public class FieldMapServlet extends SlingAllMethodsServlet {
    private final Logger LOGGER = LoggerFactory.getLogger(FieldMapServlet.class);
    @Reference
    private CustomerFieldMappingService customerFieldMappingService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        String type = request.getParameter("type");
        if(StringUtils.isEmpty(type)) {
            List fieldMapList = customerFieldMappingService.getFieldMappingList();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), fieldMapList);
        } else {
            // get the type
            FieldMappingDTO dto = customerFieldMappingService.getMapping(type);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), dto);
        }
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        ObjectMapper mapper = new ObjectMapper();
        FieldMappingDTO dto = mapper.readValue(sb.toString(), FieldMappingDTO.class);

        customerFieldMappingService.saveOrUpdateMapping(dto.getCqType(), dto.getEsType(), mapper.writeValueAsString(dto.getChildren()));
        LOGGER.info("get data: " + dto.toString());
    }
}
