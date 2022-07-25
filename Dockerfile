FROM openjdk:8-jdk-alpine

EXPOSE 8080

COPY target/monitoring-0.0.1-SNAPSHOT.jar /monitoring.jar

ENTRYPOINT java -jar /monitoring.jar