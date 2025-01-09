package Web.Controller;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Filter for validating account-related requests.
 * This filter ensures that the request contains all the required parameters (accountId, accountNumber, accountBalance, accountType)
 * and that the numerical values are in the correct format.
 * It is applied to URLs matching "/account/*".
 *
 * @author mahs
 * @version 1.0
 * @since 1.0l
 */

@WebFilter(filterName = "AccountFilter", urlPatterns = "/account/*")
public class AccountFilter implements Filter {
    // Logger to track events related to filtering
    private static final Logger logger = LogManager.getLogger(AccountFilter.class);

    /**
     * Initializes the filter.
     * This method is called once when the filter is created.
     */
    @Override
    public void init(FilterConfig filterConfig) {
        // Log filter initialization
        logger.info("AccountFilter initialized.");
    }

    /**
     * Processes incoming requests and checks if the necessary parameters are present and valid.
     * If validation fails, an error message is returned, and the request is not passed further.
     *
     * @param request  The request being processed.
     * @param response The response to be sent.
     * @param chain    The filter chain for further processing of the request.
     * @throws IOException      If an I/O error occurs during the filtering process.
     * @throws ServletException If a servlet error occurs during the filtering process.
     */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Cast to HttpServletRequest and HttpServletResponse to access HTTP-specific methods
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // Log request method and URI for debugging and monitoring
        logger.info("Processing request: {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());
        // Retrieve parameters from the request
        String accountIdStr = httpRequest.getParameter("accountId");
        String accountNumberStr = httpRequest.getParameter("accountNumber");
        String accountBalanceStr = httpRequest.getParameter("accountBalance");
        String accountType = httpRequest.getParameter("accountType");
        // Check if any of the required parameters are missing or empty
        if (accountIdStr == null || accountIdStr.isEmpty() || accountNumberStr == null || accountNumberStr.isEmpty() ||
                accountBalanceStr == null || accountBalanceStr.isEmpty() || accountType == null || accountType.isEmpty()) {
            // Log the missing input data
            logger.warn("Invalid input data: accountId={}, accountNumber={}, accountBalance={}, accountType={}", accountIdStr, accountNumberStr, accountBalanceStr, accountType);
            // Send an error response to the client with an HTML message
            httpResponse.setContentType("text/html");
            httpResponse.getWriter().println("<html><body><h1 style='color: red;'>Invalid input data. Please check the values provided.</h1></body></html>");
            // Stop further processing of the request
            return;
        }
        // Validate numerical values: accountId, accountNumber, and accountBalance
        try {
            // Try parsing accountId as an integer
            Integer.parseInt(accountIdStr);
            // Try parsing accountNumber as an integer
            Integer.parseInt(accountNumberStr);
            // Try parsing accountBalance as a double
            Double.parseDouble(accountBalanceStr);
            // Log that input validation has passed
            logger.info("Input validation passed for accountId={}, accountNumber={}, accountBalance={}, accountType={}", accountIdStr, accountNumberStr, accountBalanceStr, accountType);

        } catch (NumberFormatException e) {
            // Log an error if the number format is invalid
            logger.error("Invalid number format in input: accountId={}, accountNumber={}, accountBalance={}",
                    accountIdStr, accountNumberStr, accountBalanceStr, e);
            // Send an error response to the client with a message indicating the number format error
            httpResponse.setContentType("text/html");
            httpResponse.getWriter().println("<html><body><h1 style='color: red;'>Invalid number format in the input.</h1></body></html>");
            // Stop further processing of the request
            return;
        }
        // Log that the request is being passed to the next filter or servlet
        logger.info("Passing request to the next filter or servlet.");
        // Continue processing the request by passing it to the next filter or servlet in the chain
        chain.doFilter(request, response);
    }

    /**
     * Destroys the filter.
     * This method is called once when the filter is destroyed.
     */

    @Override
    public void destroy() {
        // Log filter destruction
        logger.info("AccountFilter destroyed.");
    }
}

