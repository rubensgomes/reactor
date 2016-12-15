/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: Handle.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * A handle can be an I/O handle (Socket), a file, and so on.
 * Regardless of the type of the handle being used in the system it
 * must have a unique integer id.
 *
 * @author Rubens Gomes
 */
public abstract class Handle
{

    /**
     * Gets the unique id that identifies this handle in the system.
     *
     * @return the id of the handle.
     */
    public abstract int getId();

    /**
     * Default dummy implementation.
     *
     * Certain Handles (e.g., ServerSocket) should accept the
     * connection and return a newly created Socket Handle.
     *
     * @return the handle
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SocketException the socket exception
     * @see ServerSocket#accept()
     */
    protected Handle accept()
      throws IOException, SocketException
    {
        return this;
    }

    /**
     * Default dummy implementation.
     *
     * Certain Handles (e.g., ServerSocket and Socket) should close.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @see ServerSocket#close()
     * @see Socket#close()
     */
    protected void close()
      throws IOException
    {
        return;
    }

}
