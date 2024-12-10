# Build stage
FROM gradle:jdk14 AS builder

WORKDIR /app

COPY ./services/solving-service-pddl4j .
COPY ./common/kotlin /common/kotlin

# Build without tests
RUN gradle -Pbuildprofile=COMPOSE -x test clean build --info

# Runtime stage
FROM gradle:jdk14

LABEL org.opencontainers.image.source=https://github.com/PlanX-Universe/planx-toolbox
LABEL org.opencontainers.image.description="PlanX Toolbox Solving PDDL4J Service"
LABEL org.opencontainers.image.licenses=MIT

ENV STAGE=COMPOSE

RUN groupadd -g 9999 planxuser && \
    useradd -r -u 9999 -g planxuser planxuser
RUN mkdir /data /app
RUN chown planxuser:planxuser /data /app

# Copy the jar to /app directory
COPY --from=builder --chown=planxuser:planxuser /app/build/libs/solving-service-pddl4j-0.0.1.jar /app/

USER planxuser

WORKDIR /app

## for REST
#EXPOSE 8090

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${STAGE}", "/app/solving-service-pddl4j-0.0.1.jar"]