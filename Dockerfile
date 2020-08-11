FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine-slim

ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

RUN apk update
RUN apk add git

WORKDIR /usr/src/mura-web

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew build -x test
