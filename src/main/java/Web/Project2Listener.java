package Web;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Project2Listener is a listener for monitoring the lifecycle events of the application context and HTTP sessions.
 * This listener tracks the number of active sessions (online users) and logs relevant events.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

@WebListener()
public class Project2Listener implements ServletContextListener, HttpSessionListener {
    // Logger to record the lifecycle events and session data
    private static final Logger logger = LogManager.getLogger(Project2Listener.class);
    // Variable to track the number of online users (active sessions)
    private static int onlineUsers = 0;

    /**
     * This method is called when the web application context is initialized.
     * It is used for any setup or logging required when the application starts.
     *
     * @param sce The event object containing the ServletContext being initialized.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Log the application context initialization event
        logger.info("Application context initialized.");
    }

    /**
     * This method is called when the web application context is destroyed.
     * It is used for cleanup tasks or logging when the application shuts down.
     *
     * @param sce The event object containing the ServletContext being destroyed.
     */

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Log the application context destruction event
        logger.info("Application context destroyed.");
    }

    /**
     * This method is called when an HTTP session is created.
     * It increments the number of online users and logs the session creation.
     *
     * @param se The event object containing the session being created.
     */

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // Synchronize the onlineUsers count to ensure thread safety
        synchronized (Project2Listener.class) {
            // Increment the online users count
            onlineUsers++;
        }
        // Log the creation of a session along with the current number of online users
        logger.info("Session created. Current online users: {}", onlineUsers);
    }

    /**
     * This method is called when an HTTP session is destroyed.
     * It decrements the number of online users and logs the session destruction.
     *
     * @param se The event object containing the session being destroyed.
     */

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // Synchronize the onlineUsers count to ensure thread safety
        synchronized (Project2Listener.class) {
            if (onlineUsers > 0) {
                // Decrement the online users count only if it's greater than 0
                onlineUsers--;
            }
        }
        // Log the destruction of a session along with the current number of online users
        logger.info("Session destroyed. Current online users: {}", onlineUsers);
    }
}