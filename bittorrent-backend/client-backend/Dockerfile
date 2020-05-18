FROM openjdk:11

RUN mkdir client

ARG PORT
#We cannot pass ARG to ENTRYPOINT command
ENV ENV_PORT=$PORT

ADD target/client-backend-0.0.1-SNAPSHOT.jar /client/client.jar

EXPOSE ${ENV_PORT}

ENTRYPOINT java -jar -Dserver.port=${ENV_PORT} /client/client.jar
