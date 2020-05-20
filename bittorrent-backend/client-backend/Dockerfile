FROM openjdk:11

RUN mkdir client

ARG PORT
ARG TRACKER_PREFIX
#We cannot pass ARG to ENTRYPOINT command
ENV ENV_PORT=$PORT
ENV ENV_TRACKER_PREFIX=$TRACKER_PREFIX

ADD target/client-backend-0.0.1-SNAPSHOT.jar /client/client.jar

EXPOSE ${ENV_PORT}

ENTRYPOINT java -jar -DtrackerUrlPrefix=${ENV_TRACKER_PREFIX} -Dserver.port=${ENV_PORT} /client/client.jar
