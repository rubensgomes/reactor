/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: AcceptorStrategyAbstractFactory.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;

/**
 * An Abstract Factory interface used for creating Acceptor
 * Strategy objects.
 *
 * @author Rubens Gomes
 */
public interface AcceptorStrategyAbstractFactory
{

    /**
     * Creates a new CreationStrategy object.
     *
     * @return the creation strategy used by the Acceptor
     * when creating a new Service Handler.
     */
    public abstract CreationStrategy createCreationStrategy();

    /**
     * Creates a new ConcurrencyStrategy object.
     *
     * @return the concurrency strategy used by the Acceptor
     * when activating a Service Handler to handle the client-
     * server communication protocol.
     */
    public abstract ConcurrencyStrategy createConcurrencyStrategy();

}
