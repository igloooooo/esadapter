package au.com.iglooit.espower.esadapter.servlet;

import au.com.iglooit.espower.esadapter.core.dto.statistic.KeywordsDTO;
import au.com.iglooit.espower.esadapter.service.statistic.QueryKeywordsService;
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
        @Property(name = "sling.servlet.paths", value = "/bin/esadapter/history/keyword", label = "ES Adapter - Query History - keywords", description = "ES Adapter - Query History - keywords"),
        @Property(name = "service.description", value = "ES Adapter - Query History - keywords"),
        @Property(name = "sling.auth.requirements", value = "-/bin/esadapter/history/keyword"),
        @Property(name = "label", value = "ES Adapter - Query History - keywords")})
public class QueryKeywordsServlet extends SlingSafeMethodsServlet {

    @Reference
    private QueryKeywordsService queryKeywordsService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        List<KeywordsDTO> result =  queryKeywordsService.top5Keywords();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), result);
    }
}
