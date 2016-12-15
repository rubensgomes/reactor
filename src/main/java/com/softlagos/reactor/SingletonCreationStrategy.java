/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: SingletonCreationStrategy.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This is the singleton creation strategy used to
 * create service handlers.
 *
 * @author Rubens Gomes
 */
public final class SingletonCreationStrategy
    implements CreationStrategy
{
    /** The logger. */
    private static final Logger logger =
            LogManager.getLogger(SingletonCreationStrategy.class);

    /**
     * Instantiates a new singleton creation strategy.
     *
     * @param service_handler_class the service handler class
     */
    public SingletonCreationStrategy(
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
    public synchronized ServiceHandler create(SocketHandle socket_handle)
        throws InstantiationException
    {
        if(s_is_handler_created)
        {
            return v_service_handler;
        }

        try
        {
            v_service_handler = v_service_handler_class.newInstance();
            v_service_handler.open(socket_handle);
            s_is_handler_created = true;
        }
        catch( IllegalAccessException ex )
        {
            String msg = "Error creating service handler: " +
                ex.getMessage();
            logger.error(msg, ex);
            throw new RuntimeException(ex);
        }

        return v_service_handler;
    }


  // ----------> private <---------- //
  private final Class<? extends ServiceHandler> v_service_handler_class;
  private static volatile boolean s_is_handler_created = false;
  private ServiceHandler v_service_handler;
}


