/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: CreationStrategy.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

/**
 * This interface provides the behaviour to be implemented by a
 * strategy object resposinble for creating a service handler.
 *
 * @author Rubens Gomes
 */
public interface CreationStrategy
{

    /**
     * Creates a service handler concrete object using the respective
     * creation strategy.
     *
     * @param socket_handle the I/O socket handle used for the client-
     * server communication protocol.
     * @return the service handler to handle the IO communication
     * protocol with the client.
     * @throws InstantiationException if it is not possible to
     * instantiate a service handler class.
     */
    public abstract ServiceHandler create(SocketHandle socket_handle)
        throws InstantiationException;
}
