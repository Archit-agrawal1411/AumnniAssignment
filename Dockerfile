# Stage 1: Build the app using Maven
FROM maven:3.8.4-amazoncorretto-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and the source code
COPY pom.xml .
COPY src ./src

# Build the jar file using Maven
RUN mvn clean package -DskipTests

# Stage 2: Run the app using Amazon Corretto 17
FROM amazoncorretto:17

# Set the working directory inside the container
WORKDIR /app

# Copy the generated jar file from the build stage
COPY --from=build /app/target/employee-management-1.0-SNAPSHOT.jar /app/employee-management-1.0-SNAPSHOT.jar

# Copy the config file into the container
COPY src/main/resources/config.yaml /app/config.yaml

# Expose the required port
EXPOSE 8080

# Set the default configuration file path environment variable
ENV CONFIG_PATH=/app/config.yaml

# Run the application with the configuration (use sh -c to expand the environment variable)
CMD ["sh", "-c", "java -Djava.management.disableCgroupMetrics=true -jar employee-management-1.0-SNAPSHOT.jar server $CONFIG_PATH"]
