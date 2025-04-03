FROM openjdk:17

WORKDIR /app/HRM/notification_service

COPY target/notification_service-0.0.1-SNAPSHOT.jar notification_service.jar

ENTRYPOINT ["java", "-jar", "/app/HRM/notification_service/notification_service.jar", "-Dspring.config.location=src/main/resources/application.properties"]

