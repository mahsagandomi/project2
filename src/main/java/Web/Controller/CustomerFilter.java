package Web.Controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * CustomerFilter ensures that only authenticated users can access pages under "/customer/*" URL pattern.
 * It checks if the user has a valid session with an attribute "user" set. If not, it redirects to the login page.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

@WebFilter(filterName = "CustomerFilter", urlPatterns = "/customer/*")
public class CustomerFilter implements Filter {
    // Logger for tracking events
    private static final Logger logger = LogManager.getLogger(CustomerFilter.class);

    /**
     * Initializes the filter, logging the filter's initialization.
     *
     * @param filterConfig Filter configuration object.
     */
    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("Initializing CustomerFilter...");
    }

    /**
     * Filters incoming requests to ensure that only authenticated users can access customer-related pages.
     * If the user is not authenticated (no valid session with "user" attribute), they are redirected to the login page.
     *
     * @param request  Incoming servlet request.
     * @param response Incoming servlet response.
     * @param chain    The filter chain, allowing the request to proceed if authentication is successful.
     * @throws IOException      if an input or output error occurs during request processing.
     * @throws ServletException if the filter chain execution fails.
     */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Cast request to HttpServletRequest
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // Cast response to HttpServletResponse
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // Log the request method and URI
        logger.debug("Processing request: {} {}", httpRequest.getMethod(), httpRequest.getRequestURI());
        // Get the current session, if available, without creating a new one (false flag)
        HttpSession session = httpRequest.getSession(false);
        // Check if session is null or if the session does not have the "user" attribute set (not authenticated)
        if (session == null || session.getAttribute("user") == null) {
            // Log unauthorized access attempt
            logger.warn("Unauthorized access attempt to: {}", httpRequest.getRequestURI());
            // Redirect to the login page
            httpResponse.sendRedirect("/login");
            // Stop further processing of the request
            return;
        }
        // Log the valid session (authenticated user)
        logger.info("User session is valid for user: {}", session.getAttribute("user"));
        // If authentication is successful, proceed to the next filter or servlet in the chain
        chain.doFilter(request, response);
    }

    /**
     * Destroys the filter and logs that it is being destroyed.
     */

    @Override
    public void destroy() {
        logger.info("Destroying CustomerFilter...");
    }
}
