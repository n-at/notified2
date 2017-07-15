notified2
=========

Email notification daemon.

Building
========

JDK >= 1.8 required. Run:

    ./mvnw clean package
    
Running
=======

Compiled jar is executable so notified can be run directly:

    cd target
    ./notified.jar
    
Or run it with java:

    java -Xmx64m -jar target/notified.jar  

More info about installation options is in official Spring Boot 
[documentation](http://docs.spring.io/spring-boot/docs/1.5.4.RELEASE/reference/htmlsingle/#deployment-install).

Configuration
=============

Configuration sample with useful comments can be found in `application.sample.yml`.

License
=======

BSD
