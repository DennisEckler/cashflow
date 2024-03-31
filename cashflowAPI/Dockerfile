FROM gradle:8.5-jdk21 AS build
WORKDIR /home/gradle/src
COPY build.gradle.kts ./
COPY src ./src
RUN gradle build -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jdk-alpine
RUN apk --no-cache add curl  # Install curl package
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]
