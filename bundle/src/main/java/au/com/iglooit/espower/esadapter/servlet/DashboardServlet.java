package au.com.iglooit.espower.esadapter.servlet;

import au.com.iglooit.espower.esadapter.core.dto.statistic.StatisticBoardDTO;
import au.com.iglooit.espower.esadapter.service.statistic.StatisticBoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

@Component(metatype = false)
@Service(javax.servlet.Servlet.class)
@Properties({
        @Property(name = "sling.servlet.paths", value = "/bin/esadapter/dashboard", label = "Dashboard Data Servlet", description = "ES Adapter - Dashboard Data"),
        @Property(name = "service.description", value = "ES Adapter - Dashboard Data"),
        @Property(name = "sling.auth.requirements", value = "-/bin/esadapter/dashboard"),
        @Property(name = "label", value = "ES Adapter - Dashboard Data")})
public class DashboardServlet extends SlingSafeMethodsServlet {
    private final Logger LOGGER = LoggerFactory.getLogger(DashboardServlet.class);

    @Reference
    private StatisticBoardService statisticBoardService;
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        StatisticBoardDTO dto = statisticBoardService.generateDashboardData();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, dto);
    }
}
