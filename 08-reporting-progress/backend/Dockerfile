FROM amazoncorretto:21-alpine-jdk

ENV TZ=Asia/Yangon

RUN apk update && apk add --no-cache tzdata \
    && addgroup -S spring && adduser -S spring -G spring \
    && mkdir -p /var/lib/application/storage && chown -R spring:spring /var/lib/application/storage

USER spring:spring

COPY target/*.jar app.jar
EXPOSE 8080/tcp

ENTRYPOINT ["java","-jar", "/app.jar"]
