FROM amazoncorretto:11-alpine-jdk
MAINTAINER Miguel Angel Sereno <msereno@kyda.mx>
ENV APP_HOME /app

# Run as non-root
RUN addgroup -S appuser && adduser -S appuser -G appuser
RUN mkdir -m 0755 -p ${APP_HOME}
RUN mkdir -m 0755 -p ${APP_HOME}/bin
RUN mkdir -m 0755 -p ${APP_HOME}/config
RUN mkdir -m 0755 -p ${APP_HOME}/logs
RUN chown -R appuser:appuser ${APP_HOME}
USER appuser

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ${APP_HOME}/bin/app.jar

WORKDIR ${APP_HOME}
ENTRYPOINT ["java","-jar","/app/bin/app.jar"]