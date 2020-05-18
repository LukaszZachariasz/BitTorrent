FROM openjdk:11

RUN mkdir tracker

ARG PORT
#We cannot pass ARG to ENTRYPOINT command
ENV ENV_PORT=$PORT

ADD target/tracker-backend-0.0.1-SNAPSHOT.jar /tracker/tracker.jar

EXPOSE ${ENV_PORT}

ENTRYPOINT java -jar -Dserver.port=${ENV_PORT} /tracker/tracker.jar
