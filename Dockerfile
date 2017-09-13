FROM java:openjdk-8-jre

MAINTAINER Alexey Nurgaliev <atnurgaliev@gmail.com>

WORKDIR /app

ADD target/notified.jar /app/notified.jar

RUN mkdir -m 0777 templates logs

VOLUME ["/app/templates", "/app/logs"]

EXPOSE 8080

CMD java ${JAVA_OPTS} -jar notified.jar
