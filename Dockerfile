# --- Stage 1: Build the Spring Boot application ---
# Use a JDK image for compiling and packaging the application.
# eclipse-temurin is a popular choice for OpenJDK builds.
FROM eclipse-temurin:21-jdk-jammy AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and project files
# This helps in caching dependencies. If build.gradle or settings.gradle
# don't change, Docker can reuse the cached layer for dependencies.
COPY gradlew .
COPY gradle gradlew.bat ./gradle/
COPY build.gradle settings.gradle ./

# Copy the source code (this will trigger a new build if source changes)
COPY src ./src

# Set permissions for the Gradle wrapper (important for Linux/macOS)
RUN chmod +x gradlew

# Build the application
# Use --no-daemon for CI/CD environments to avoid lingering processes.
# Use -x test to skip tests during the build if you run them separately.
RUN ./gradlew bootJar --no-daemon -x test

# --- Stage 2: Create the final runtime image ---
# Use a JRE image for running the application. It's much smaller than a JDK image.
# eclipse-temurin:21-jre-jammy is a good choice for JRE.
FROM eclipse-temurin:21-jre-jammy AS run

# Set the working directory for the application
WORKDIR /app

# Expose the port your Spring Boot application listens on (default is 8080)
EXPOSE 8080

# Copy the built JAR from the 'build' stage
# The bootJar task typically places the JAR in build/libs/
ARG JAR_FILE=app/build/libs/*.jar
COPY --from=build ${JAR_FILE} app.jar

# Define the entrypoint to run the Spring Boot application
# Using 'java -jar' is standard.
# The `java -Djava.security.egd=file:/dev/./urandom` part is a common
# optimization for Spring Boot apps to speed up startup by improving
# randomness source, especially in container environments.
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

# You can also use a CMD instruction for more flexibility, e.g.:
# CMD ["java", "-jar", "app.jar"]

