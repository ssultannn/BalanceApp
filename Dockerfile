# Use OpenJDK 21 image for building the application
FROM openjdk:21-slim AS build

# Set the working directory in the container
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy the pom.xml file and download the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the entire project and build it
COPY . .
RUN mvn clean package -DskipTests

# Create a new image with OpenJDK 21 for running the application
FROM openjdk:21-slim

# Set the working directory for the application
WORKDIR /app

# Copy the built jar file from the previous build stage
COPY --from=build /app/target/cicdapp.jar app.jar

# Run the application (no profile needed if default is H2)
ENTRYPOINT ["java", "-jar", "app.jar"]
