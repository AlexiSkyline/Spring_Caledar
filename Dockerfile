ARG APP_NAME=Spring_Calendar

FROM openjdk:18-jdk-alpine as builder

ARG APP_NAME

WORKDIR /app/$APP_NAME

COPY ./pom.xml /app
COPY ./.mvn ./.mvn
COPY ./mvnw .
COPY ./pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:18-jdk-alpine

ARG APP_NAME

WORKDIR /app
RUN mkdir ./logs

ARG TARGET_FOLDER=/app/$APP_NAME/target
COPY --from=builder $TARGET_FOLDER/Spring_Calendar-0.0.1-SNAPSHOT.jar .
ARG PORT_APP=8080
ENV PORT $PORT_APP
EXPOSE $PORT

CMD ["java", "-jar", "Spring_Calendar-0.0.1-SNAPSHOT.jar"]