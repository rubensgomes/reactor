/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: ServerSocketHandle.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A Server Socket I/O Handle that is bound to a port
 * on the server end point to handle incomding client
 * connection requests.
 *
 * @author Rubens Gomes
 */
public final class ServerSocketHandle extends Handle
{
    /** The logger. */
    private static final Logger logger =
            LogManager.getLogger(ServerSocketHandle.class);

    /**
     * Instantiates a new server socket handle.
     *
     * @param port the port server socket will bind to.
     */
    public ServerSocketHandle(int port)
    {
        try
        {
            if(logger.isTraceEnabled())
            {
                logger.trace("creating server socket handle");
            }

            v_srv_socket = new ServerSocket(port);
            v_port = port;

            if(logger.isTraceEnabled())
            {
                logger.trace("constructed.");
            }

        }
        catch(IOException ex)
        {
            String msg = "Error creating server socket at port [ " +
                    port + "] : " + ex.getMessage();
            logger.error(msg, ex);
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see com.softlagos.reactor.Handle#getId()
     */
    @Override
    public int getId()
    {
        return v_port;
    }

    /* (non-Javadoc)
     * @see com.softlagos.reactor.Handle#accept()
     */
    @Override
    protected Handle accept()
      throws IOException, SocketException
    {
        Socket socket = v_srv_socket.accept();

        if(logger.isTraceEnabled())
        {
            logger.trace("creating new socket handle");
        }

        SocketHandle new_handle = new SocketHandle(socket);
        return new_handle;
    }


    // ------ >>> Private <<< ------
    private final ServerSocket v_srv_socket;
    private final int v_port;
}
