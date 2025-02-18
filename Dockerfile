FROM eclipse-temurin:23-jre-alpine
EXPOSE 8080
COPY backend/target/ApplicationManagementApp.jar ApplicationManagementApp.jar
ENTRYPOINT ["java", "-jar", "ApplicationManagementApp.jar"]
