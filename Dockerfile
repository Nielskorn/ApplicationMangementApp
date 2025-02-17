FROM --platform=linux/amd64 openjdk:23
RUN apt-get update && apt-get upgrade -y && apt-get clean && rm -rf /var/lib/apt/lists/*
EXPOSE 8080
COPY backend/target/ApplicationManagementApp.jar ApplicationManagementApp.jar
ENTRYPOINT ["java", "-jar", "ApplicationManagementApp.jar"]
