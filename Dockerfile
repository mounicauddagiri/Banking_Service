# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and src files to the container at /app
COPY pom.xml .
COPY src ./src

# Copy the JAR file from the host into the container at /app
COPY target/CodeScreen_xpz8pzqh-1.0.0-jar-with-dependencies.jar .

# Copy the MySQL connector JAR file from the host into the container at /app
COPY lib/mysql-connector-j-8.3.0.jar /app/

# Specify the command to run your JAR file
CMD ["java", "-jar", "CodeScreen_xpz8pzqh-1.0.0-jar-with-dependencies.jar"]
