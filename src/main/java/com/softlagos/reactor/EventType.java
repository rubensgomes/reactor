/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: EventType.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

/**
 * An enum for the different types of events generated in the system.
 * <p>
 * @see EventHandler
 *
 * @author Rubens Gomes
 */
public enum EventType
{
    ACCEPT,
    CLOSE,
    REMOVED,
    SIGNAL;
}
