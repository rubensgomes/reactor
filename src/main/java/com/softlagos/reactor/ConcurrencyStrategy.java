/**
 * Copyright (C) 1999-2016 Rubens Gomes <rubens.s.gomes@gmail.com>.
 * All Rights Reserved.
 *
 * File: ConcurrencyStrategy.java
 *
 * Author: Rubens Gomes
 */
package com.softlagos.reactor;


/**
 * This interface provides the type of concurrency schemes
 * (thread on demand, thread pool).
 * <p>
 * The Acceptor uses a specific Concurrency Strategy to activate
 * Service Handlers to handle the client-server communication
 * protocol.
 * <p>
 *
 * @author Rubens Gomes
 */
public interface ConcurrencyStrategy
{

  /**
   * Activates the given service handler by using the respective
   * concurrency scheme.
   *
   * @param service_handler the service_handler
   */
  public abstract void activate(ServiceHandler service_handler);
}
