package au.com.iglooit.espower.esadapter.servlet;

import au.com.iglooit.espower.esadapter.service.ESQueryService;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.elasticsearch.action.search.SearchResponse;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;

@Component(metatype = false)
@Service(javax.servlet.Servlet.class)
@Properties({
        @Property(name = "sling.servlet.paths", value = "/bin/esadapter/queryexplain", label = "ES Adapter - Query Explain", description = "ES Adapter - Query Explain"),
        @Property(name = "service.description", value = "ES Adapter - Query Explain"),
        @Property(name = "sling.auth.requirements", value = "-/bin/esadapter/queryexplain"),
        @Property(name = "label", value = "ES Adapter - Query Explain")})
public class QueryExplainServlet extends SlingAllMethodsServlet {
    @Reference
    private ESQueryService esQueryService;


    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        SearchResponse response1 = esQueryService.search(sb.toString());
        response.getWriter().write(response1.toString());
    }

}
