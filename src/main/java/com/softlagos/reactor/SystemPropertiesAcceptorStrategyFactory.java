/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: SystemPropertiesAcceptorStrategyFactory.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.softlagos.Constants;
import com.softlagos.util.SystemProperties;

/**
 * A concrete Abstract Factory that makes use of the
 * system.properties files to determine what strategies
 * to use.
 *
 * @author Rubens Gomes
 */
public final class SystemPropertiesAcceptorStrategyFactory
 implements AcceptorStrategyAbstractFactory
{
    /** The logger. */
    private static final Logger logger =
            LogManager.getLogger(SystemPropertiesAcceptorStrategyFactory.class);

    /**
     * Instantiates a new acceptor strategy factory.
     *
     * @param service_handler_class the service_handler_class
     */
    public SystemPropertiesAcceptorStrategyFactory(
            Class<? extends ServiceHandler> service_handler_class)
    {
        if( service_handler_class == null )
        {
            throw new IllegalArgumentException("service_handler_class cannot be null");
        }

        if(logger.isTraceEnabled())
        {
            logger.trace("loading property file: " +
                    Constants.PROPERTIES_FILE);
        }

        SystemProperties props = SystemProperties.instance();

        String concurrency_strategy =
              props.getProperty(Constants.ACCEPTOR_CONCURRENCY_STRATEGY);

        if( concurrency_strategy == null )
        {
            String msg = "Cannot instantiate AcceptorStrategyFactory because " +
                    Constants.ACCEPTOR_CONCURRENCY_STRATEGY +
                    " property is not set.";
            logger.error(msg);
            throw new RuntimeException(msg);
        }
        else if(concurrency_strategy.trim().equals(Constants.THREAD_ON_DEMAND) )
        {
            v_concurrency_strategy = new ThreadOnDemandConcurrencyStrategy();
        }
        else if (concurrency_strategy.trim().equals(Constants.THREAD_POOL) )
        {
            v_concurrency_strategy = new ThreadPoolConcurrencyStrategy();
        }
        else
        {
            String msg = "Cannot instantiate AcceptorStrategyFactory because " +
                    Constants.ACCEPTOR_CONCURRENCY_STRATEGY +
                    " property is not acceptable.";
            logger.error(msg);
            throw new RuntimeException(msg);
        }

        String create_strategy = props
                .getProperty(Constants.ACCEPTOR_CREATION_STRATEGY);
        if( create_strategy == null )
        {
            String msg = "Cannot instantiate AcceptorStrategyFactory because " +
                    Constants.ACCEPTOR_CREATION_STRATEGY +
                    " property is not set.";
            throw new RuntimeException(msg);
        }
        else if(create_strategy.trim().equals(Constants.ON_DEMAND) )
        {
            v_creation_strategy = new OnDemandCreationStrategy(service_handler_class);
        }
        else if (create_strategy.trim().equals(Constants.SINGLETON) )
        {
            v_creation_strategy = new SingletonCreationStrategy(service_handler_class);
        }
        else
        {
            String msg = "Cannot instantiate AcceptorStrategyFactory because " +
                    Constants.ACCEPTOR_CREATION_STRATEGY +
                    " property is not acceptable.";
            logger.error(msg);
            throw new RuntimeException(msg);
        }
    }

    /**
     * @return the creation strategy used by the Acceptor
     * when creating a new Service Handler.
     */
    public CreationStrategy createCreationStrategy()
    {
        return v_creation_strategy;
    }

    /**
     * @return the concurrency strategy used by the Acceptor
     * when activating a Service Handler to handle the client-
     * server communication protocol.
     */
    public ConcurrencyStrategy createConcurrencyStrategy()
    {
        return v_concurrency_strategy;
    }


    // ---------->>> Private Section <<<---------- //
    private final ConcurrencyStrategy v_concurrency_strategy;
    private final CreationStrategy v_creation_strategy;
}
