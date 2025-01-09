package Business.Event;

import Entity.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.LoadEvent;
import org.hibernate.event.spi.LoadEventListener;

import java.io.Serializable;

/**
 * Custom implementation of the {@link LoadEventListener} that logs the details of
 * an {@link Account} object when it is loaded from the database.
 * This listener is triggered when an entity is loaded, and it logs the event if
 * the loaded entity is of type {@link Account}.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */


public class LoadEventListenerImp implements LoadEventListener, Serializable {

    private static final long serialVersionUID = 1L;
    // Logger instance to log messages and events.
    private static final Logger logger = LogManager.getLogger(LoadEventListenerImp.class);


    /**
     * This method is called when an entity is loaded from the database.
     *
     * @param e    The {@link LoadEvent} that contains details about the loaded entity.
     * @param type The {@link LoadType} indicating the type of load operation.
     * @throws HibernateException if there is an error during the load operation.
     */
    @Override
    public void onLoad(LoadEvent e, LoadType type) throws HibernateException {
        // Log that the onLoad method was called.
        logger.info("onLoad is called.");
        // Retrieve the result object from the load event.
        Object obj = e.getResult();
        // Check if the loaded object is an instance of Account.
        if (obj instanceof Account) {
            // Cast the object to Account and log its details.
            Account account = (Account) obj;
            logger.info(account);
        }
    }

}
