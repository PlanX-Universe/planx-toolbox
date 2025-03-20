# PlanX Toolbox

## About

PlanX Toolbox is a comprehensive, open-source platform for building, integrating, and deploying AI planning systems. It offers modular, service-oriented functionalities that support end-to-end AI planning processes for both academic research and industrial applications. Instead of relying on a single, monolithic implementation, this toolbox allows users to create, test, and deploy customized AI planning pipelines by selecting or developing only the services they need. This flexible approach enables users to focus on the specific functionalities that interest them—whether it be modeling, solving, validation, or monitoring—without being encumbered by extraneous features.

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Architecture](#architecture)
4. [Supported Use Cases](#supported-use-cases)
5. [Usage](#usage)
6. [How to Cite](#how-to-cite)
7. [Relevant Literature](#relevant-literature)
8. [License](#license)

## Overview

The **PlanX Toolbox** simplifies the process of constructing AI planning systems by offering the following:

- **Encapsulation of Planning Functionalities**: Each functionality is delivered as an independent, interoperable, and portable service.
- **Modularity and Flexibility**: Services are standalone yet composable, allowing integration into larger systems.
- **Open Standards**: Communication between services is achieved using JSON.
- **Ease of Deployment**: Supports containerization through Docker for quick setup.

## Features

1. **Planning Services**:
   - **Parsing**: Analyzes PDDL problems to ensure compliance with specified rules.
   - **Converting**: Encodes and transforms input planning problems into usable formats.
   - **Solving**: Provides algorithms to generate solutions for planning problems.
   - **Plan Validation**: Validates plans for correctness and feasibility.
   - **Modeling**: Web-based IDE for creating, editing, and refining PDDL models.
   - **Visualization**: Offers a UI for interacting with and debugging planning solutions.
   - **Monitoring**: Tracks system health, service status, and messaging middleware.

   _Notice: Some planning services are implemented using existing planning software, such as [PDDL4J](https://github.com/pellierd/pddl4j) and [Web planner](https://icaps17.icaps-conference.org/workshops/UISP/uisp17proceedings.pdf#page=36), to leverage established planning algorithms and visualization tools._

2. **Interoperability**:
   - Built on containerization and message-oriented middleware (RabbitMQ).
   - Services are implemented using Spring Boot, Kotlin (back-end), and Angular (front-end).

3. **Extensibility**:
   - **Horizontal scaling**: Add new services or algorithms.
   - **Vertical scaling**: Integrate additional functionalities, such as execution monitoring.

## Architecture

The toolbox consists of multiple services communicating via RabbitMQ. Services include:

- **Front-end Services**: Modeling, Visualization, and System Monitoring.
- **Back-end Services**: Parsing, Converting, Solving, Plan Validation, and Managing.
- **Messaging Component**: Ensures asynchronous, reliable communication between services.

Each service operates independently and can be composed into larger systems. An example is shown in [Quick Start with Docker Compose](#quick-start-with-docker-compose).

## Supported Use Cases

1. **Building a Standalone AI Planning System**:
   Use a subset of services (e.g., Parsing, Converting, and Solving) to address a specific planning problem.

2. **Expanding Existing Systems**:
   Add additional solvers, algorithms, or validation services for enhanced capabilities.

3. **Advanced AI Planning Systems**:
   Combine modeling, visualization, and monitoring to create a comprehensive, user-friendly solution.

4. **Integration with External Systems**:
   Use PlanX Toolbox as a middleware or modular component in larger applications.

## Usage

### Prerequisites

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

### Quick Start with Docker Compose

1. Clone the repository:

   ```bash
   git clone https://github.com/PlanX-Universe/planx-toolbox.git
   cd planx-toolbox/compose
   ```
2. Start the services:

   ```bash
   docker-compose up
   ```

3. Open [http://localhost:4200](http://localhost:4200) in your browser to access the PlanX UI.

## How to Cite

- [Georgievski, I. (2023). PlanX: A toolbox for building and integrating AI planning systems. In 2023 IEEE International Conference on Service-Oriented System Engineering (SOSE) (pp. 130-134). IEEE.](https://doi.org/10.1109/SOSE58276.2023.00022)

## Relevant Literature

- [Georgievski, I. (2025) Engineering AI Planning Systems. Habilitation Thesis. University of Stuttgart.]()

- [Georgievski, I. (2022) Towards Engineering AI Planning Functionalities as Services. In Service-Oriented Computing – ICSOC 2022 Workshops, of Lecture Notes in Computer Science, pages 225–236.](https://doi.org/10.1007/978-3-031-26507-5_18)

- [Georgievski, I. and Breitenbücher, U. (2021) A Vision for Composing, Integrating, and Deploying AI Planning Functionalities. In IEEE International Conference on Service-Oriented System Engineering, pages 166–171.]()

- [Graef, S. and Georgievski, I. (2021) Software Architecture for Next-Generation AI Planning Systems. Technical Report 2102.10985, arXiv.](https://arxiv.org/pdf/2102.10985.pdf)

- [Magnaguagno, M. C., Pereira, R. F., Móre, M. D., and Meneguzzi, F. (2020) Web planner: A tool to develop, visualize, and test classical planning domains. In Knowledge Engineering Tools and Techniques for AI Planning (pp. 209-227).](https://doi.org/10.1007/978-3-030-38561-3_11)

- [Pellier, D. and Fiorino, H. (2018) PDDL4J: a planning domain description library for java. Journal of Experimental & Theoretical Artificial Intelligence, 30(1), 143-176.](https://doi.org/10.1080/0952813X.2017.1409278)

## License

PlanX Toolbox is distributed under the MIT License. See [LICENSE](/LICENSE) for details.
