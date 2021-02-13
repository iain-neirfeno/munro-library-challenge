FROM openjdk:14-jdk-alpine

ENV PORT=""

RUN addgroup -S www && adduser -S www -G www
USER www:www
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT exec java -jar /app.jar --server.port=$PORT
