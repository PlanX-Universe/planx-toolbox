# Build stage
FROM gradle:jdk14 AS builder

WORKDIR /app

COPY ./services/parsing-service .

COPY ./common/kotlin /common/kotlin

# Build without tests
RUN gradle -Pbuildprofile=COMPOSE -x test clean build --info

# Runtime stage
FROM gradle:jdk14

LABEL org.opencontainers.image.source=https://github.com/PlanX-Universe/planx-toolbox
LABEL org.opencontainers.image.description="PlanX Toolbox Parsing Service"
LABEL org.opencontainers.image.licenses=MIT

ENV STAGE=COMPOSE

RUN groupadd -g 9999 planxuser && \
    useradd -r -u 9999 -g planxuser planxuser
RUN mkdir /data /app
RUN chown planxuser:planxuser /data /app

# Copy only the jar from builder stage
COPY --from=builder --chown=planxuser:planxuser /app/build/libs/parsing-service-0.0.1.jar /app/

USER planxuser

WORKDIR /app

## for REST
#EXPOSE 8090

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${STAGE}", "/app/parsing-service-0.0.1.jar"]