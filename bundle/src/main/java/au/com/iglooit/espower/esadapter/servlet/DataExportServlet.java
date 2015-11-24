package au.com.iglooit.espower.esadapter.servlet;

import au.com.iglooit.espower.esadapter.core.dto.DataExportResponse;
import au.com.iglooit.espower.esadapter.service.DataExportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

@Component(metatype = false)
@Service(javax.servlet.Servlet.class)
@Properties({
        @Property(name = "sling.servlet.paths", value = "/bin/esadapter/export", label = "Data Export Servlet", description = "ES Adapter - Data Exporter"),
        @Property(name = "service.description", value = "ES Adapter - Data Export"),
        @Property(name = "sling.auth.requirements", value = "-/bin/esadapter/export"),
        @Property(name = "label", value = "ES Adapter - Data Export")})
public class DataExportServlet extends SlingAllMethodsServlet {
    private static final String PATH_PARAMETER = "path";
    private static final String TYPE_PARAMETER = "type";
    private static final String DRY_RUN = "dry";
    // this part need to change to give all result
    @Reference
    private DataExportService dataExportService;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String path = request.getParameter(PATH_PARAMETER);
        Boolean bDryRun = DRY_RUN.equalsIgnoreCase(request.getParameter(TYPE_PARAMETER));
        if (StringUtils.isNotBlank(path)) {
            DataExportResponse dataExportResponse = dataExportService.exportData(path, bDryRun);

            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, dataExportResponse);
        }

    }
}
