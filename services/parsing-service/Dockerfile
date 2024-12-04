# Build stage
FROM gradle:jdk14 AS builder

WORKDIR /app

COPY ./services/parsing-service .

COPY ./common/kotlin /common/kotlin

# Build without tests
RUN gradle -Pbuildprofile=COMPOSE -x test clean build --info

# Runtime stage
FROM openjdk:14

ENV STAGE=COMPOSE

RUN groupadd -g 9999 planxuser && \
    useradd -r -u 9999 -g planxuser planxuser
RUN mkdir /data
RUN chown planxuser:planxuser /data

USER planxuser

# Copy only the jar from builder stage
COPY --from=builder /app/build/libs/parsing-service-0.0.1.jar /

## for REST
#EXPOSE 8090

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${STAGE}", "parsing-service-0.0.1.jar"]
