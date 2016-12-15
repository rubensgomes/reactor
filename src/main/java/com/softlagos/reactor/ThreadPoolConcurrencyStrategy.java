/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: ThreadPoolConcurrencyStrategy.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.softlagos.threadpool.ThreadPool;

/**
 * The Thread Pool Concurrency Strategy uses a thread from the thread
 * pool to activate and run a service handler.
 *
 * @author Rubens Gomes
 */
public final class ThreadPoolConcurrencyStrategy
    implements ConcurrencyStrategy
{

    /** The logger. */
    private static final Logger logger =
            LogManager.getLogger(ThreadPoolConcurrencyStrategy.class);

    /**
     * Instantiates a new thread pool concurrency strategy.
     */
    public ThreadPoolConcurrencyStrategy()
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

        ThreadPool thread_pool = ThreadPool.instance();

        if(logger.isTraceEnabled())
        {
            logger.trace("pushing task into thread pool");
        }

        thread_pool.pushTask(service_handler.getTask());
    }

}
