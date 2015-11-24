package au.com.iglooit.espower.esadapter.servlet;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.servlet.ServletException;
import java.io.IOException;

@Component(metatype = false)
@Service(javax.servlet.Servlet.class)
@Properties({
        @Property(name = "sling.servlet.paths", value = "/bin/esadapter/querybuilder", label = "Field Mapping Servlet", description = "ES Adapter - Field Mapping"),
        @Property(name = "service.description", value = "ES Adapter - Field Mapping"),
        @Property(name = "label", value = "Migration Script Servlet") })
public class QueryBuilderServlet extends SlingAllMethodsServlet {
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }
}
