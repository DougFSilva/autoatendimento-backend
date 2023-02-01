FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
ARG PROFILE_ACTIVE=prod
ENV spring.profiles.active=${PROFILE_ACTIVE}
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]