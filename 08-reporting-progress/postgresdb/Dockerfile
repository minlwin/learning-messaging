FROM postgres:alpine

RUN apk update && apk add --no-cache tzdata

ENV POSTGRES_DB=progressdb
ENV POSTGRES_USER=progressdb
ENV POSTGRES_PASSWORD=secret

EXPOSE 5432/tcp