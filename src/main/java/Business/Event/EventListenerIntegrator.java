package Business.Event;


import Business.AccountBeanImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

import java.util.List;

/**
 * Custom event listener integrator that registers custom event listeners for
 * Hibernate event types such as SAVE, LOAD, and REFRESH.
 * This class implements {@link Integrator} and {@link IntegratorProvider}
 * to integrate custom event listeners into the Hibernate session factory.
 *
 * @author mahsa
 * @version 1.0
 * @since 1.0
 */


public class EventListenerIntegrator implements Integrator, IntegratorProvider {

    private static final Logger logger = LogManager.getLogger(AccountBeanImpl.class);

    /**
     * Integrates custom event listeners into the Hibernate event system.
     *
     * @param metadata        The Hibernate metadata containing entity mappings.
     * @param sessionFactory  The Hibernate session factory being initialized.
     * @param serviceRegistry The service registry used by Hibernate to register services.
     */
    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor
            sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        logger.info("Integrating custom event listeners...");
        // Obtain the EventListenerRegistry to manage Hibernate event listeners.
        EventListenerRegistry eventListenerRegistry =
                serviceRegistry.getService(EventListenerRegistry.class);
        // Register a custom listener for the SAVE event.
        eventListenerRegistry.getEventListenerGroup(EventType.SAVE)
                .appendListener(new SaveUpdateEventListenerImp());
        logger.info("SAVE event listener registered.");
        // Register a custom listener for the LOAD event.
        eventListenerRegistry.getEventListenerGroup(EventType.LOAD)
                .appendListener(new LoadEventListenerImp());
        logger.info("LOAD event listener registered.");
        // Register a custom listener for the REFRESH event.
        eventListenerRegistry.getEventListenerGroup(EventType.REFRESH)
                .appendListener(new RefreshEventListenerImp());
        logger.info("REFRESH event listener registered.");
    }


    /**
     * Disintegrates custom event listeners from the Hibernate session factory.
     *
     * @param sessionFactory  The Hibernate session factory being destroyed.
     * @param serviceRegistry The service registry used by Hibernate to unregister services.
     */
    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory,
                             SessionFactoryServiceRegistry serviceRegistry) {
        logger.info("Disintegrating custom event listeners...");
        // No specific action required for disintegration.
    }

    // Singleton instance of this integrator.
    public static final EventListenerIntegrator INSTANCE = new EventListenerIntegrator();

    /**
     * Provides the list of integrators to be used by Hibernate.
     *
     * @return A list containing this custom integrator.
     */
    @Override
    public List<Integrator> getIntegrators() {
        logger.info("Returning custom integrator.");
        return List.of(this);
    }
}

