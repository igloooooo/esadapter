package au.com.iglooit.espower.esadapter.servlet.example;

import au.com.iglooit.espower.esadapter.service.acl.AEMACLServiceImpl;
import au.com.iglooit.espower.esadapter.service.example.ExampleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.elasticsearch.action.search.SearchResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component(metatype = false)
@Service(javax.servlet.Servlet.class)
@Properties({
        @Property(name = "sling.servlet.paths", value = "/bin/esadapter/example/authsearch", label = "Example Auth Search Servlet", description = "ES Adapter - Example Auth Search"),
        @Property(name = "service.description", value = "ES Adapter - Example Auth Search"),
        @Property(name = "sling.auth.requirements", value = "-/bin/esadapter/example/authsearch"),
        @Property(name = "label", value = "ES Adapter - Example Auth Search")})
public class AuthenticatedSearchServlet extends SlingSafeMethodsServlet {
    @Reference
    private AEMACLServiceImpl aemaclService;
    @Reference
    private ExampleService exampleService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String content = request.getParameter("q");
        List<String> denyPath = aemaclService.getAccessPath();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        SearchResponse result = exampleService.query(content, denyPath);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(result.toString());
    }
}
