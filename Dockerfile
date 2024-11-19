FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/taskManagementSystem-0.0.1-SNAPSHOT.jar /app/taskManagementSystem.jar

ENTRYPOINT ["java", "-jar", "/app/taskManagementSystem.jar"]

EXPOSE 8080