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

Usage
=====

First, you need to setup a *notification template* and *transport* in `application.yml`.

When notified is started, you can send it a POST request with notification data. This data should contain template name
(`api_template` field), template key (`api_key` field), transport configuration fields (if needed). Other request
fields can be used for rendering the notification body.

License
=======

BSD
