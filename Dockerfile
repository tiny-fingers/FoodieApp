FROM maven:3.8.5-openjdk-17 AS builder
LABEL authors="tinyfingers"
LABEL maintainer="tinyfingers"

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM --platform=linux/arm64/v8 openjdk:22-ea-17-slim

WORKDIR /app

ENV DATABASE_URL=${DATABASE_URL}
ENV DATABASE_USER=${DATABASE_USER}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}
ENV SERVER_PORT=${SERVER_PORT}

COPY --from=builder /app/target/*.jar app.jar

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java","-jar","/app/app.jar"]