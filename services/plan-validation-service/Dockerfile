# Build stage
FROM gradle:jdk14 AS builder

WORKDIR /app

COPY ./services/plan-validation-service .

COPY ./common/kotlin /common/kotlin

# Build without tests
RUN gradle -Pbuildprofile=COMPOSE -x test clean build --info

# Runtime stage
FROM aigupf/starter-kit:latest

LABEL org.opencontainers.image.source=https://github.com/PlanX-Universe/planx-toolbox
LABEL org.opencontainers.image.description="PlanX Toolbox Plan Validation Service"
LABEL org.opencontainers.image.licenses=MIT

ENV STAGE=COMPOSE


# Install JAVA
RUN apt update && \
    apt upgrade --yes && \
    apt-get install software-properties-common --yes && \
    add-apt-repository ppa:openjdk-r/ppa
RUN apt update && \
    apt-get install openjdk-15-jdk-headless --yes
RUN export JAVA_HOME=/usr/lib/jvm/openjdk-15-jdk && \
    export PATH=\$PATH:\$JAVA_HOME/bin

RUN groupadd -g 9999 planxuser && \
    useradd -r -u 9999 -g planxuser planxuser
RUN mkdir /data /app
RUN chown planxuser:planxuser /data /app

# Copy only the jar from builder stage
#COPY ./build/libs/validating-cap-val-0.0.1.jar /data/microservice.jar
COPY --from=builder --chown=planxuser:planxuser /app/build/libs/plan-validation-service-0.0.1.jar /app/

USER planxuser

# create folder for temporary-pddl-files
RUN mkdir /data/temp

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${STAGE}", "/app/plan-validation-service-0.0.1.jar"]
