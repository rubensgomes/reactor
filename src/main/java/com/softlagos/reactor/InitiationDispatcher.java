/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: InitiationDispatcher.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Singleton.
 *
 * An InitiationDispatcher is a Reactor that is
 * responsible for dispatching of application-specific
 * event handlers in response to events.
 *
 * @author Rubens Gomes
 * @see Reactor
 */
public final class InitiationDispatcher
  implements Reactor, Runnable
{
    /** The logger. */
    private static final Logger logger =
            LogManager.getLogger(InitiationDispatcher.class);

    /**  The singleton instance. */
    private static final InitiationDispatcher s_instance =
            new InitiationDispatcher();

    /** The Constant THREAD_GROUP_NAME. */
    private static final String THREAD_GROUP_NAME = "Dispatcher";

    /**
     * Returns the singleton instance.
     *
     * @return the initiation dispatcher singleton.
     */
    public static InitiationDispatcher instance()
    {
        return s_instance;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.softlagos.reactor.Reactor#registerHandler(com.softlagos.
     * reactor.EventHandler)
     */
    @Override
    public synchronized void registerHandler(final EventHandler handler,
            final EventType type)
    {
        if(handler == null)
        {
            throw new IllegalArgumentException("handler cannot be null.");
        }

        if(type == null)
        {
            throw new IllegalArgumentException("type cannot be null.");
        }

        Integer key = handler.getHandle().getId();

        if(logger.isTraceEnabled())
        {
            logger.trace("registering handler with handle id [" + key +
                         "] and event type [" + type + "]");
        }

        EventHandlerItem item = new EventHandlerItem(type, handler);

        if(v_event_handlers.get(key) != null)
        {
            throw new IllegalArgumentException(
                     "A hanlder has already been registered at id [" +
                             key + "]");
        }

        v_event_handlers.put(key, item);
    }

    /*
     * (non-Javadoc)
     * @see com.softlagos.reactor.Reactor#removeHandler(com.softlagos.
     * reactor.EventHandler)
     */
    @Override
    public synchronized void removeHandler(final EventHandler handler)
    {
        if(handler == null)
        {
            return;
        }

        Integer handle_key = handler.getHandle().getId();

        if(logger.isTraceEnabled())
        {
            logger.trace("attempting to de-register handler with id [" +
                    handle_key + "]");
        }

        List<Integer> delete_keys = new ArrayList<Integer>();

        EventHandlerItem item = null;
        EventHandler event_handler = null;
        for(Integer key : v_event_handlers.keySet())
        {
            item = v_event_handlers.get(key);
            event_handler = item.getEventHandler();

            if(handler.equals(event_handler))
            {
                event_handler.handleEvent(event_handler.getHandle(),
                                          EventType.REMOVED,
                                          "being removed from reactor");
                delete_keys.add(key);
            }
        }

        for(Integer key : delete_keys)
        {
            v_event_handlers.remove(key);
            if(logger.isInfoEnabled())
            {
                logger.info("removed handler with id [" + key + "]");
            }
        }

    }

    /*
     * (non-Javadoc)
     * @see com.softlagos.reactor.Reactor#removeAllHandlers()
     */
    @Override
    public synchronized void removeAllHandlers()
    {
        if(logger.isInfoEnabled())
        {
            logger.info("removing all handlers...");
        }

        // needs to close all handles associated with handlers first.
        closeHandles();

        v_event_handlers.clear();

        if(logger.isInfoEnabled())
        {
            logger.info("all handlers have been removed.");
        }
    }

    /*
     * (non-Javadoc)
     * @see com.softlagos.reactor.Reactor#handleEvents()
     */
    @Override
    public void handleEvents()
    {
        if(logger.isTraceEnabled())
        {
            logger.trace("handling events...");
        }

        for(Integer key : v_event_handlers.keySet())
        {
            EventHandlerItem handler_item = v_event_handlers.get(key);

            if(handler_item.getEventType() == EventType.ACCEPT)
            {
                // We must use the handlers map key as the thread name
                // because we need to refer to that key from within the
                // run() method once the thread is started.
                Thread dispatcher = new Thread(v_thread_group,
                                               this,
                                               "" + key);
                if(logger.isTraceEnabled())
                {
                    logger.trace("start event handling for handle id: "  +
                            key);
                }

                dispatcher.start();
            }

            // TODO: Implement other event types.

        }
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run()
    {
        Thread current_thread = Thread.currentThread();

        Integer key = Integer.parseInt(current_thread.getName());
        EventHandlerItem item = v_event_handlers.get(key);

        if(item.getEventType() == EventType.ACCEPT)
        {

            if(logger.isTraceEnabled())
            {
                logger.trace("running thread to ACCEPT event");
            }

            EventHandler handler = item.getEventHandler();
            Handle handle = handler.getHandle();

            while (true)
            {

                try
                {
                    // the following call "waits" for a connection to be
                    // made on the event handler handle (e.g. ServerSocket).
                    Handle new_handle = handler.getHandle().accept();

                    // dispatching event to event handler
                    handler.handleEvent(new_handle,
                                        EventType.ACCEPT,
                                        "new handle created.");
                }
                catch(SocketException ex)
                {
                    if(logger.isErrorEnabled())
                    {
                        logger.error("SocketException: " +
                                ex.getMessage());
                    }

                    // exception raised due to the socket being closed while
                    // the serversocket was "waiting" for a connection.
                    handler.handleEvent(handle,
                                        EventType.CLOSE,
                                        ex.getMessage());
                    break; // break from while loop
                }
                catch(IOException ex)
                {
                    if(logger.isErrorEnabled())
                    {
                        logger.error("IOException: " + ex.getMessage());
                    }
                    handler.handleEvent(handle,
                                        EventType.SIGNAL,
                                        ex.getMessage());
                    break; // break from while loop
                }
            }

        }

    }

    /*
     * (non-Javadoc)
     * @see com.softlagos.reactor.Reactor#close()
     */
    @Override
    public synchronized void close()
    {
        if(v_is_closed)
        {
            throw new IllegalStateException("Reactor already closed.");
        }

        // Closes all handles first
        if(logger.isTraceEnabled())
        {
            logger.trace("closing all handles...");
        }
        closeHandles();

        // interrupts all threads
        if(logger.isTraceEnabled())
        {
            logger.trace("interrupting all threads...");
        }
        v_thread_group.interrupt();

        try
        {
            // allow time for all the threads to stop nicelly
            if(logger.isTraceEnabled())
            {
                logger.trace("sleeping for " + CLOSE_WAIT_TIME +
                             " msecs");
            }
            Thread .sleep(CLOSE_WAIT_TIME);
        }
        catch(InterruptedException ex)
        {
            if(logger.isInfoEnabled())
            {
                logger.info("interrupted exception: " + ex.getMessage());
            }
        }
        finally
        {
            if(logger.isTraceEnabled())
            {
                logger.trace("destroying thread group.");
            }
            v_thread_group.destroy();
        }

        if(logger.isInfoEnabled())
        {
            logger.info("closed");
        }

        v_is_closed = true;
    }

    // ------ >>> Private <<< ------

    /**
     * Instantiates a new initiation dispatcher.
     */
    private InitiationDispatcher()
    {
        v_event_handlers =
                new HashMap<Integer, EventHandlerItem>();
        v_thread_group = new ThreadGroup(THREAD_GROUP_NAME);
        v_is_closed = false;

        if(logger.isTraceEnabled())
        {
            logger.trace("instantiated");
        }
    }

    /**
     * Calls all event handlers to close their handles.
     */
    private void closeHandles()
    {

        // Closes all handles
        for(Integer key : v_event_handlers.keySet())
        {
            if(logger.isInfoEnabled())
            {
                logger.info("closing handler with id: " + key);
            }

            EventHandlerItem item = v_event_handlers.get(key);
            EventHandler handler = item.getEventHandler();

            handler.handleEvent(handler.getHandle(),
                                EventType.CLOSE,
                                "closing all handlers");
        }

    }

    /** The v_event_handlers. */
    private final Map<Integer, EventHandlerItem> v_event_handlers;

    /** The v_thread_group. */
    private final ThreadGroup v_thread_group;

    /** The v_is_closed. */
    private boolean v_is_closed;
}
