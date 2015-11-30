package au.com.iglooit.espower.esadapter.servlet.example;

import au.com.iglooit.espower.esadapter.service.example.ExampleService;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.elasticsearch.action.search.SearchResponse;

import javax.servlet.ServletException;
import java.io.IOException;

@Component(metatype = false)
@Service(javax.servlet.Servlet.class)
@Properties({
        @Property(name = "sling.servlet.paths", value = "/bin/esadapter/example/attachmentsearch",
                label = "Example Attachment Search Servlet", description = "ES Adapter - Example Attachment Search"),
        @Property(name = "service.description", value = "ES Adapter - Example Attachment Search"),
        @Property(name = "sling.auth.requirements", value = "-/bin/esadapter/example/attachmentsearch"),
        @Property(name = "label", value = "ES Adapter - Example Attachment Search")})
public class AttachmentSearchServlet extends SlingSafeMethodsServlet {
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
