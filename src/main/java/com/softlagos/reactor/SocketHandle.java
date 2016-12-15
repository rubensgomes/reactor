/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: SocketHandle.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

import java.io.IOException;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A Socket I/O Handle that is created by a Server
 * Socket I/O Handle as result of accepting new
 * incoming connections .
 *
 * @author Rubens Gomes
 */
public final class SocketHandle extends Handle
{
    /** The logger. */
    private static final Logger logger =
            LogManager.getLogger(SocketHandle.class);

    /**
     * Instantiates a new socket handle.
     *
     * @param socket the socket
     */
    public SocketHandle(Socket socket)
    {
        if(socket == null)
        {
            throw new IllegalArgumentException("socket cannot be null.");
        }

        v_socket = socket;

        if(logger.isTraceEnabled())
        {
            logger.trace("constructed.");
        }

    }

    /* (non-Javadoc)
     * @see com.softlagos.reactor.Handle#getId()
     */
    @Override
    public int getId()
    {
        return v_socket.getPort();
    }

    /* (non-Javadoc)
     * @see com.softlagos.reactor.Handle#close()
     */
    public void close()
      throws IOException
    {
        if(logger.isTraceEnabled())
        {
            logger.trace("closing");
        }

        v_socket.close();
    }

    /**
     * @return the IO Socket associated with this handle.
     */
    public Socket getSocket()
    {
        return v_socket;
    }

    // ------ >>> Private <<< ------
    /** The I/O Socket */
    private final Socket v_socket;

}
