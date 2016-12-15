/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: EventHandler.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

/**
 * This class specifies an interface that the Reactor uses to
 * dispatch handleEvent callback method in response to an
 * event generated in the system. Event Handlers should be
 * pre-registered with the Reactor to handle specific events.
 */
public abstract class EventHandler
{

    /**
     * Handles the given event type.
     *
     * @param handle the handle associated with the event.
     * @param evtType the event type that is being handled.
     * @param message some text message that may be associated
     * with the event.  The message could be empty or null.
     */
    public abstract void handleEvent(final Handle handle,
            final EventType evtType, String message);


    /**
     * Returns the handle being used by the event handler.
     *
     * @return the handle used by the event handler.
     */
    public abstract Handle getHandle();

}
