FROM gradle:8.5-jdk21 AS build
WORKDIR /home/gradle/src
COPY app/.gradle .gradle
COPY app/build.gradle.kts ./
COPY app/src ./src
RUN gradle build --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jdk-alpine
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
