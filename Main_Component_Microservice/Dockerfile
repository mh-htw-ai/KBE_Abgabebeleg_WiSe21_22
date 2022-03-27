FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY target/logback-spring.xml .
ENTRYPOINT ["java","-jar","/app.jar"]