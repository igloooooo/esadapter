package au.com.iglooit.espower.esadapter.servlet;

import au.com.iglooit.espower.esadapter.core.dto.MappingDTO;
import au.com.iglooit.espower.esadapter.service.ESMappingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(metatype = false)
@Service(javax.servlet.Servlet.class)
@Properties({
        @Property(name = "sling.servlet.paths", value = "/bin/esadapter/systemmapping", label = "ES Adapter - System Mapping", description = "ES Adapter - System Mapping"),
        @Property(name = "service.description", value = "ES Adapter - System Mapping"),
        @Property(name = "sling.auth.requirements", value = "-/bin/esadapter/systemmapping"),
        @Property(name = "label", value = "ES Adapter - System Mapping")})
public class SystemMappingServlet extends SlingAllMethodsServlet {
    @Reference
    private ESMappingService esMappingService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        List<MappingDTO> result = new ArrayList<MappingDTO>();
        String index = request.getParameter("index");
        String type = request.getParameter("type");
        if(StringUtils.isEmpty(type)) {
            // get all types of index
            result = esMappingService.getMappings(index);
        } else {
            result.add(esMappingService.getMapping(index, type));
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), result);
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }
}
