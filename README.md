# PlanX Toolbox

PlanX is a comprehensive toolbox designed for building, integrating, and deploying AI planning systems. It offers modular, service-oriented functionalities that make it easier to compose advanced AI planning workflows. PlanX is open-source and built to support both academic research and industrial applications, addressing challenges in AI planning such as modeling, solving, validation, and monitoring.

## Table of Contents
1. [Overview](#overview)
2. [Features](#features)
3. [Architecture](#architecture)
4. [Supported Use Cases](#supported-use-cases)
5. [Usage](#usage)
6. [Documentation](#documentation)
7. [How to Cite](#how-to-cite)
8. [Relevant Literature](#relevant-literature)
9. [License](#license)


---

## Overview

The **PlanX Toolbox** simplifies the process of constructing AI planning systems by offering the following:

- **Encapsulation of Planning Functionalities**: Each functionality is delivered as an independent, interoperable, and portable service.
- **Modularity and Flexibility**: Services are standalone yet composable, allowing integration into larger systems.
- **Open Standards**: Communication between services is achieved using JSON.
- **Ease of Deployment**: Supports containerization through Docker for quick setup.



---

## Features

1. **Planning Services**:
   - **Parsing**: Analyzes PDDL problems to ensure compliance with specified rules.
   - **Converting**: Encodes and transforms input planning problems into usable formats.
   - **Solving**: Provides algorithms to generate solutions for planning problems.
   - **Plan Validation**: Validates plans for correctness and feasibility.
   - **Modeling**: Web-based IDE for creating, editing, and refining PDDL models.
   - **Visualization**: Offers a UI for interacting with and debugging planning solutions.
   - **Monitoring**: Tracks system health, service status, and messaging middleware.

2. **Interoperability**:
   - Built on containerization and message-oriented middleware (RabbitMQ).
   - Services are implemented using Spring Boot, Kotlin (back-end), and Angular (front-end).

3. **Extensibility**:
   - Horizontal scaling: Add new services or algorithms.
   - Vertical scaling: Integrate additional functionalities, such as execution monitoring.

---

## Architecture

The toolbox consists of multiple services communicating via RabbitMQ. Services include:
- **Front-end Services**: Modeling, Visualization, and System Monitoring.
- **Back-end Services**: Parsing, Converting, Solving, Plan Validation, and Managing.
- **Messaging Component**: Ensures asynchronous, reliable communication between services.

Each service operates independently and can be composed into larger systems. An exapmle is shown in [Quick Start with Docker Compose](#quick-start-with-docker-compose).

---

## Supported Use Cases

1. **Building a Standalone AI Planning System**:
   Use a subset of services (e.g., Parsing, Converting, and Solving) to address a specific planning problem.

2. **Expanding Existing Systems**:
   Add additional solvers, algorithms, or validation services for enhanced capabilities.

3. **Advanced AI Planning Systems**:
   Combine modeling, visualization, and monitoring to create a comprehensive, user-friendly solution.

4. **Integration with External Systems**:
   Use PlanX as a middleware or modular component in larger applications.

---

## Usage

### Prerequisites
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

### Quick Start with Docker Compose
1. **Clone the repository**:
   ```bash
   git clone https://github.com/PlanX-Universe/planx-toolbox.git
   cd planx-toolbox/compose
   docker compose up
   ```
   open [http://localhost:4200](http://localhost:4200) in your browser to access the PlanX UI.


## Documentation

- Academic References: Learn more about the system's design and methodology in the following papers:
    - [Paper Link](#)
    - [Paper Link](#)


## How to Cite


Georgievski, I. (2023, July). PlanX: A toolbox for building and integrating AI planning systems. In 2023 IEEE International Conference on Service-Oriented System Engineering (SOSE) (pp. 130-134). IEEE.



## Relevant Literature

- [Paper Link](#)
- [Paper Link](#)


## License 

PlanX is distributed under the MIT License. See [LICENSE](/LICENSE) for details.
