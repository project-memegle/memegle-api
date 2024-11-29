FROM openjdk:17-slim

EXPOSE 8080 8443

ARG JAR_FILE=build/libs/*.jar

ADD ${JAR_FILE} /memegle-api.jar
ADD keystore.p12 /keystore.p12

ENV JAVA_OPTS="-Dspring.profiles.active=prod -Duser.timezone=Asia/Seoul"

RUN echo ${JAVA_OPTS}

CMD ["sh", "-c", "java -jar memegle-api.jar ${JAVA_OPTS}"]