package au.com.iglooit.espower.esadapter.servlet.example;

import au.com.iglooit.espower.esadapter.service.acl.AEMACLServiceImpl;
import au.com.iglooit.espower.esadapter.service.example.ExampleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.elasticsearch.action.search.SearchResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service(javax.servlet.Servlet.class)
@Properties({
        @Property(name = "sling.servlet.paths", value = "/bin/esadapter/example/filesearch", label = "Example File Search Servlet", description = "ES Adapter - Example File Search"),
        @Property(name = "service.description", value = "ES Adapter - Example File Search"),
        @Property(name = "sling.auth.requirements", value = "-/bin/esadapter/example/filesearch"),
        @Property(name = "label", value = "ES Adapter - Example File Search")})
public class FileSearchServlet extends SlingSafeMethodsServlet {

    @Reference
    private ExampleService exampleService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String content = request.getParameter("q");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        SearchResponse result = exampleService.queryAttachment(content);
        response.getWriter().write(result.toString());
    }
}
