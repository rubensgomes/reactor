/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: ThreadOnDemandConcurrencyStrategy.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.softlagos.threadpool.OnDemandTaskThread;

/**
 * The thread on demand concurrency strategy spawns a new
 * thread to activate and run a service handler.
 *
 * @author Rubens Gomes
 */
public final class ThreadOnDemandConcurrencyStrategy
    implements ConcurrencyStrategy
{

    /** The logger. */
    private static final Logger logger =
            LogManager.getLogger(ThreadOnDemandConcurrencyStrategy.class);

    /**
     * Instantiates a new thread on demand concurrency strategy.
     */
    public ThreadOnDemandConcurrencyStrategy()
    {
    }

    /* (non-Javadoc)
     * @see com.softlagos.reactor.ConcurrencyStrategy#activate(com.softlagos.reactor.ServiceHandler)
     */
    @Override
    public void activate(ServiceHandler service_handler)
    {
        if(service_handler == null)
        {
            throw new IllegalArgumentException("service_handler cannot be null.");
        }

        if(logger.isTraceEnabled())
        {
            logger.trace("creating OnDemandTaskThread");
        }

        OnDemandTaskThread thread = new OnDemandTaskThread(service_handler.getTask());

        if(logger.isTraceEnabled())
        {
            logger.trace("starting OnDemandTaskThread");
        }

        thread.start();
    }
}
