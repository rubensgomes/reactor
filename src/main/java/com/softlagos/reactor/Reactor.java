/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: Reactor.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

/**
 * Reactor provides a high level interface to listen and react to
 * events generated in the system.  Once the Reactor detects an
 * event, it dispatches that event to one of the registered event
 * handlers.
 * <p>
 * For example, a Reactor may be implemented by a "Dispatcher"
 * passive network reactive object.  A network dispatcher object
 * binds and listens on a given IP/Port address, and "waits" for an
 * incoming connection request (event) to arrive.  Once a connection
 * request (event) arrives, the network dispatcher reacts to that
 * connection request by handling it to the appropriate "Event
 * Handler".
 * <p>
 * Reactor is a design pattern commonly used to implement reactive
 * networking communication services.  Further information about
 * this design pattern can be found at:
 * <p>
 * <pre>
 * D. C. Schmidt,  Reactor:  An Object Behavioral Pattern for
 * Concurrent  Event  Demultiplexing  and  Event  Handler
 * Dispatching,  in Pattern  Languages  of  Program  Design
 * (J.  O. Coplien and D. C. Schmidt, eds.),
 * pp. 52eading, MA: Addison-Wesley, 1995.
 * </pre>
 * <p>
 *
 * @author Rubens Gomes
 */
public interface Reactor
{

    /**
     * Time to sleep while the reactor is closing.
     */
    public final int CLOSE_WAIT_TIME = 3000; // msecs

    /**
     * Registers an event handler (e.g., Acceptor) which is
     * interested in receiving and processing that event. At most
     * one handler for a given handle ID can be registered.
     * Duplicate handle IDs are not permitted.
     *
     * @param handler the event handler that will handle the event.
     * @param type the event type that the event handler is
     * interested in.
     */
    public abstract void registerHandler(final EventHandler handler,
            final EventType type);

    /**
     * De-registers the given event handler.
     *
     * @param handler the event handler previously registered.
     */
    public abstract void removeHandler(final EventHandler handler);

    /**
     * De-registers all event handlers.
     */
    public abstract void removeAllHandlers();

    /**
     * Initiates the handling of events.
     */
    public abstract void handleEvents();

    /**
     * Close all resources used by the Reactor,  and stops handling
     * further events.  Basically it shuts down the Reactor.
     */
    public abstract void close();
}
