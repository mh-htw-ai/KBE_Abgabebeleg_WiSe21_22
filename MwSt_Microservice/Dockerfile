FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/logback-spring.xml .
ENTRYPOINT ["java","-jar","/app.jar"]