FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY build/libs/cashflowAPI-0.0.1.jar /app/cashflow-api.jar
ENTRYPOINT ["java", "-jar", "/app/cashflow-api.jar"]
