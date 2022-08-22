FROM openjdk:16-alpine3.13

LABEL maintainer="luckykr1234@gmail.com"

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} rekorapi.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /rekorapi.jar"]

