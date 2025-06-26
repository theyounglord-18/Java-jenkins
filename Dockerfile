FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/myapp.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
