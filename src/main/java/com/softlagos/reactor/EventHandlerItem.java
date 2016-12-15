/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: EventHandlerItem.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

/**
 * Immutable.
 *
 * The EventHandlerItem is a simple type to be stored in collections.
 *
 * @author Rubens Gomes
 */
public final class EventHandlerItem
{

    /**
     * Instantiates a new event handler item.
     *
     * @param type the event type
     * @param handler the event handler
     */
    public EventHandlerItem(final EventType type,
            final EventHandler handler)
    {
        if(type == null)
        {
            throw new IllegalArgumentException("type cannot be null.");
        }

        if(handler == null)
        {
            throw new IllegalArgumentException("handler cannot be null.");
        }

        v_type = type;
        v_handler = handler;
    }

    /**
     * Gets the event type.
     *
     * @return the event type
     */
    public EventType getEventType()
    {
        return v_type;
    }

    /**
     * Gets the event handler.
     *
     * @return the event handler
     */
    public EventHandler getEventHandler()
    {
        return v_handler;
    }

    /** The v_handler. */
    // ------ >>> Private <<< ------
    private final EventHandler v_handler;

    /** The v_type. */
    private final EventType v_type;
}
