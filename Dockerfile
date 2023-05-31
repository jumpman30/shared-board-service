# Start with a base image containing Java runtime
FROM eclipse-temurin:17.0.5_8-jre-alpine@sha256:02c04793fa49ad5cd193c961403223755f9209a67894622e05438598b32f210e

# The application's .jar file
ARG JAR_FILE=target/*.jar

# Add the application's .jar to the container
COPY ${JAR_FILE} app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
