package Web.Controller;

import Business.ICustomerBean;
import Entity.Customer;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet to generate and serve a report of customers whose account balance is greater than 10,000.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

@WebServlet(name = "BalanceReportServlet", urlPatterns = "/balanceReport")
public class CustomerReportServlet extends HttpServlet {
    // Injecting ICustomerBean to access customer-related business logic
    @EJB
    private ICustomerBean icustomerBean;
    // Logger for tracking report generation process
    private static final Logger logger = LogManager.getLogger(CustomerReportServlet.class);

    /**
     * Handles the GET request to generate and download the customer balance report as a PDF.
     *
     * @param req  The HttpServletRequest object that contains the client's request.
     * @param resp The HttpServletResponse object that is used to send the report back to the client.
     * @throws IOException If an I/O error occurs during the request-response cycle.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Report generation request received.");

        // Fetch the list of customers with an account balance greater than 10,000
        List<Customer> customers = icustomerBean.findCustomersWithBalance(10000);
        logger.debug("Number of customers retrieved for report: {}", customers.size());

        // Set content type to PDF and specify filename in the header
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "inline; filename=CustomerReport.pdf");

        // Create data source for JasperReports using the list of customers
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(customers);

        // Prepare parameters for the report
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Customers with Balance Greater than 10000");

        try {
            // Load the JRXML file (report template) from the servlet context
            logger.info("Loading report template.");
            InputStream reportStream = getServletContext().getResourceAsStream("/src/main/webapp/WEB-INF/reports/CustomerReport.jrxml");
            if (reportStream == null) {
                logger.warn("Report template not found.");
                throw new IOException("Report template not found: /WEB-INF/reports/CustomerReport.jrxml");
            }
            // Compile the Jasper report from the JRXML template
            logger.info("Compiling Jasper report.");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            // Fill the report with data and parameters
            logger.info("Filling report with data.");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export the report to a PDF and send it to the client
            logger.info("Exporting report to PDF.");
            ServletOutputStream outputStream = resp.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

            outputStream.flush();
            logger.info("Report generated and sent to client successfully.");
        } catch (JRException e) {
            // Handle JasperReports-specific errors
            logger.error("JasperReports error occurred.", e);
            showErrorPage(resp, "Error generating report: " + e.getMessage());
        } catch (IOException e) {
            // Handle I/O errors during report generation
            logger.error("I/O error occurred during report generation.", e);
            showErrorPage(resp, "I/O error: " + e.getMessage());
        }
    }

    /**
     * Displays an error page with the given message in case of an exception during report generation.
     *
     * @param resp    The HttpServletResponse object used to send the error page.
     * @param message The error message to display on the error page.
     * @throws IOException If an I/O error occurs while writing the response.
     */

    private void showErrorPage(HttpServletResponse resp, String message) throws IOException {
        logger.warn("Displaying error page with message: {}", message);
        resp.setContentType("text/html");
        try (ServletOutputStream out = resp.getOutputStream()) {
            out.println("<html>");
            out.println("<head><title>Error</title></head>");
            out.println("<body>");
            out.println("<h2>Error</h2>");
            out.println("<p>" + message + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
