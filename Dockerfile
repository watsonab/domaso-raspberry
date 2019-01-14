FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/domaso-1.0T.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar prod"]