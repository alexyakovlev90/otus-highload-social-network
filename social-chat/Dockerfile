FROM openjdk:12-alpine

ARG JAVA_OPTS
ARG JAR_FILE=target/social-chat.jar

RUN apk add --no-cache bash gawk sed grep bc coreutils busybox-extras tzdata

RUN adduser -D -g 'ay' ay

RUN mkdir /app

COPY ${JAR_FILE} /app/app.jar
RUN chown ay:ay /app/app.jar
COPY entrypoint.sh /

RUN chown ay:ay /entrypoint.sh
RUN ["chmod", "+x", "/entrypoint.sh"]
CMD ["/entrypoint.sh"]
