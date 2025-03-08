FROM amazoncorretto:17-alpine3.17 AS corretto-jdk
LABEL authors="Spring Application"

RUN apk add --no-cache binutils

RUN $JAVA_HOME/bin/jlink \
         --verbose \
         --add-modules ALL-MODULE-PATH \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /customjre

# Create a new stage to reduce the size of the final image
FROM alpine:latest AS base
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Copy the custom JRE from the previous stage
COPY --from=corretto-jdk /customjre $JAVA_HOME

ARG APPLICATION_USER=appuser
RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER

RUN mkdir /app && \
    chown -R $APPLICATION_USER /app

# Run as non-root user i.e. somebody / user:1000
USER 1000

FROM base

# Replace the jar name with the name of the jar file you are building
COPY --chown=1000:1000 ./target/pixl-pos-api-0.0.1.jar /app/app.jar
WORKDIR /app

EXPOSE 8080
ENTRYPOINT ["/jre/bin/java", "-jar", "/app/app.jar"]