package Business.Event;


import Entity.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.RefreshContext;
import org.hibernate.event.spi.RefreshEvent;
import org.hibernate.event.spi.RefreshEventListener;

import java.io.Serializable;

/**
 * Custom implementation of the {@link RefreshEventListener} that logs the details of
 * an {@link Account} object when it is refreshed in the session.
 * This listener is triggered when an entity is refreshed, and it logs the event if
 * the refreshed entity is of type {@link Account}.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class RefreshEventListenerImp implements RefreshEventListener, Serializable {


    private static final long serialVersionUID = 1L;
    // Logger instance to log messages and events.
    private static final Logger logger = LogManager.getLogger(RefreshEventListenerImp.class);

    /**
     * This method is called when an entity is refreshed in the Hibernate session.
     *
     * @param e The {@link RefreshEvent} that contains details about the refreshed entity.
     * @throws HibernateException if there is an error during the refresh operation.
     */
    @Override
    public void onRefresh(RefreshEvent e) throws HibernateException {
        // Log that the onRefresh method was called.
        logger.info("onRefresh is called.");
        // Retrieve the object being refreshed.
        Object obj = e.getObject();
        // Check if the refreshed object is an instance of Account.
        if (obj instanceof Account) {
            // Cast the object to Account and log its details.
            Account account = (Account) obj;
            logger.info(account);
        }
    }

    /**
     * This method is called when an entity is refreshed with a specific {@link RefreshContext}.
     *
     * @param refreshEvent   The {@link RefreshEvent} that contains details about the refreshed entity.
     * @param refreshContext The {@link RefreshContext} in which the refresh operation occurs.
     * @throws HibernateException if there is an error during the refresh operation.
     */

    @Override
    public void onRefresh(RefreshEvent refreshEvent, RefreshContext refreshContext) throws HibernateException {
        // Method is not implemented, but this can be used for future extensions.
    }
}


