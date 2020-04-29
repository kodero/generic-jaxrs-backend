package com.corvid.tulip.admin.model.base;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LemrIntegrator implements Integrator {
    static final Logger logger = LoggerFactory.getLogger(LemrIntegrator.class.getName());

    /*@Override
    public void integrate(Configuration configuration, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        logger.info("start integerate");
        // As you might expect, an EventListenerRegistry is the place with which event listeners are registered  It is a service
        // so we look it up using the service registry
        final EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);

        // If you wish to have custom determination and handling of "duplicate" listeners, you would have to add an implementation
        // of the org.hibernate.event.service.spi.DuplicationStrategy contract like this
        //eventListenerRegistry.addDuplicationStrategy( myDuplicationStrategy );

        // EventListenerRegistry defines 3 ways to register listeners:
        //     1) This form overrides any existing registrations with
        eventListenerRegistry.setListeners(EventType.DELETE, new SoftDeleteEventListener());
        //     2) This form adds the specified listener(s) to the beginning of the listener chain
        //eventListenerRegistry.prependListeners( EventType.DELETE, new SoftDeleteEventListener() );
        //     3) This form adds the specified listener(s) to the end of the listener chain
        //eventListenerRegistry.appendListeners( EventType.AUTO_FLUSH, myListenersToBeCalledLast );
    }*/

    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactoryImplementor, SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {
        // As you might expect, an EventListenerRegistry is the thing with which event listeners are registered  It is a
        // service so we look it up using the service registry
        final EventListenerRegistry eventListenerRegistry = sessionFactoryServiceRegistry.getService( EventListenerRegistry.class );

        //     3) This form adds the specified listener(s) to the end of the listener chain
        //eventListenerRegistry.prependListeners(EventType.DELETE, new SoftDeleteEventListener() );
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
        logger.info("empty : disintegrate");
    }
}