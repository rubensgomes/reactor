# Justification for this project

NOTE:  This code is not fully tested, and should be considered under  development code.
The source code that is provided are  mainly used for demonostrations and learning purposes only.

A Java implementation of the Socket Reactor/Aceptor design patterns used
for networking connections.   I wrote this project as a fun learning exercise.

## Installation

- Download and install Apache Maven from:
```
    https://maven.apache.org/
```
- Clone the project from my GitHub:
```
    https://github.com/rubensgomes/reactor.git
```
## Build, Test and Deploy

- To set up an eclipse project:
```
    mvn -U eclipse:eclipse
```
- To run a java build:
```
    mvn -U install
```
- To run tests:
```
    mvn test
```
- To deploy build:
```
    mvn deploy
```

# references

Further information about these patterns can be found in the 
following papers:

Acceptor 
A Design Pattern for Passively Initializing Network Services
Douglas C. Schmidt
schmidt@cs.wustl.edu
Department of Computer Science
Washington University
St. Louis, MO 63130, US


Acceptor and Connector
A Family of Object Creational Patterns
for Initializing Communication Services
Douglas C. Schmidt
schmidt@cs.wustl.edu
Department of Computer Science
Washington University
St. Louis, MO 63130, USA


D. C. Schmidt,  Reactor:  An Object Behavioral Pattern for
Concurrent  Event  Demultiplexing  and  Event  Handler  
Dispatching,  in Pattern  Languages  of  Program  Design
(J.  O. Coplien and D. C. Schmidt, eds.), 
pp. 52eading, MA: Addison-Wesley, 1995.

Reactor
An Object Behavioral Pattern for
Demultiplexing and Dispatching Handles for Synchronous Events
Douglas C. Schmidt
schmidt@cs.wustl.edu
Department of Computer Science
Washington University
St. Louis, MO 63130, USA



