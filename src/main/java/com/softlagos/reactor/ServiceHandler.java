/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: ServiceHandler.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

import com.softlagos.threadpool.Task;

// TODO: Auto-generated Javadoc
/**
 * This abstract class provides a generic interface for
 * processing services. Applications must customize this
 * class to perform a particular type of service.
 *
 * @author Rubens Gomes
 */
public abstract class ServiceHandler extends EventHandler
  implements Runnable
{

    /**
     * The open method initializes the Service Handler with the
     * IO Socket Handle used for the client-server communication
     * protocol.
     * <p>
     * It is called by an Acceptor after a connection is
     * established.  This must be done upfront before any
     * other methods are called on the service handler.
     *
     * @param socket_handle the socket_handle
     */
    public abstract void open(SocketHandle socket_handle);

    /**
     * Closes this service handler by properly closing
     * and releasing any IO handle connections.
     */
    public abstract void close();

    /**
     * Implements the protocol (conversation) between client
     * and server.
     */
    public abstract void run();

    /**
     * Gets the task.
     *
     * @return the task to be executed when handling the service.
     */
    public abstract Task getTask();
}
