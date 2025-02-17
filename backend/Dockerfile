FROM --platform=linux/amd64 openjdk:23
EXPOSE 8080
COPY backend/target/ApplicationManagementApp.jar ApplicationManagementApp.jar
ENTRYPOINT ["java", "-jar", "ApplicationManagementApp.jar"]