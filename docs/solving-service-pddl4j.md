# Solving Service
A Solving Service
solves a given planning problem. It decides on its own which
planning functionalities (e.g., parsing) would be used before
the actual solving process starts. This service’s output is a plan.
Depending on the kind of planning algorithms provided in
the PlanX toolbox, different types of plans may be produced

# PDD4J
Planning Domain Definition Language

## Prerequisites
- Java Development Kit (JDK) : Version 14 and above
- Docker
- IntelliJ IDEA

## Configuring Docker and IntelliJ IDEA

- Install RabbitMQ container on Docker with the flag --name = rabbitmq
- Check module settings in IntelliJ IDEA and make sure the right JDK is selected.