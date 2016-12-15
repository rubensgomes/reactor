/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: OnDemandCreationStrategy.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is the On demand creation strategy used to create
 * service handlers.   This class will instantiate a new
 * instance of Service Handler every time.
 *
 * @author Rubens Gomes
 */
public final class OnDemandCreationStrategy
    implements CreationStrategy
{
    /** The logger. */
    private static final Logger logger =
            LogManager.getLogger(OnDemandCreationStrategy.class);

    /**
     * Instantiates a new on demand creation strategy.
     *
     * @param service_handler_class the service handler class
     */
    public OnDemandCreationStrategy(
            Class<? extends ServiceHandler> service_handler_class)
    {
        if(service_handler_class == null)
        {
            throw new IllegalArgumentException("service_handler_class cannot be null.");
        }

        v_service_handler_class = service_handler_class;
    }

    /* (non-Javadoc)
     * @see com.softlagos.reactor.CreationStrategy#create(com.softlagos.reactor.SocketHandle)
     */
    @Override
    public ServiceHandler create(SocketHandle socket_handle)
            throws InstantiationException
    {
        ServiceHandler service_handler = null;

        try
        {
            service_handler = v_service_handler_class.newInstance();
            service_handler.open(socket_handle);
        }
        catch( IllegalAccessException ex )
        {
            String msg = "Error creating service handler: " +
                ex.getMessage();
            logger.error(msg, ex);
            throw new RuntimeException(ex);
        }

        return service_handler;
    }

    // ----------> private <---------- //
    private final Class<? extends ServiceHandler> v_service_handler_class;
}
