package au.com.iglooit.espower.esadapter.servlet;

import au.com.iglooit.espower.esadapter.core.dto.statistic.KeywordsDTO;
import au.com.iglooit.espower.esadapter.core.dto.statistic.SlowQueryDTO;
import au.com.iglooit.espower.esadapter.service.statistic.SlowQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Component(metatype = false)
@Service(javax.servlet.Servlet.class)
@Properties({
        @Property(name = "sling.servlet.paths", value = "/bin/esadapter/history/slowquery", label = "ES Adapter - Query History - Slow Query", description = "ES Adapter - Query History - Slow Query"),
        @Property(name = "service.description", value = "ES Adapter - Query History - Slow Query"),
        @Property(name = "sling.auth.requirements", value = "-/bin/esadapter/history/slowquery"),
        @Property(name = "label", value = "ES Adapter - Query History - Slow Query")})
public class SlowQueryServlet extends SlingSafeMethodsServlet {

    @Reference
    private SlowQueryService slowQueryService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        List<SlowQueryDTO> result =  slowQueryService.top5SlowQuery();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), result);
    }
}
