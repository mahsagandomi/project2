package Business.Event;

import Entity.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.*;

import java.io.Serializable;

/**
 * Custom implementation of the {@link SaveOrUpdateEventListener} that logs the details of
 * an {@link Account} object when it is saved or updated in the Hibernate session.
 * This listener is triggered whenever an entity is saved or updated in the session.
 * It logs the event if the saved/updated entity is of type {@link Account}.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */

public class SaveUpdateEventListenerImp implements SaveOrUpdateEventListener, Serializable {
    // Logger to log events related to save or update
    private static final Logger logger = LogManager
            .getLogger(SaveUpdateEventListenerImp.class);
    // Serial version UID for serialization
    private static final long serialVersionUID = 1L;


    /**
     * This method is called when an entity is saved or updated in the Hibernate session.
     *
     * @param e The {@link SaveOrUpdateEvent} that contains details about the saved or updated entity.
     * @throws HibernateException if there is an error during the save or update operation.
     */
    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent e) throws HibernateException {
        // Log that the onSaveOrUpdate method is called
        logger.info("onSaveOrUpdate is called.");
        // Get the entity from the event
        Object obj = e.getEntity();
        // Check if the entity is an instance of Account, and log its details
        if (obj instanceof Account) {
            Account account = (Account) obj;
            logger.info("Account saved or updated" + account);
        }
    }
}

