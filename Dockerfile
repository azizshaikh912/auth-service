# Use a lightweight JDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy built jar file into container
COPY target/ecommerce-0.0.1.jar app.jar

# Expose port (adjust if needed)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]