# Microservices Demo with Spring Cloud Alibaba

A comprehensive microservices demo project built with the Spring ecosystem to demonstrate enterprise-grade distributed system architecture using:

- Spring Boot 3
- Spring Cloud
- Spring Cloud Alibaba
- Nacos (Service Discovery & Configuration Management)
- OpenFeign (Service-to-Service Communication)
- Sentinel (Circuit Breaker & Fault Tolerance)
- RocketMQ (Event-Driven Messaging)
- Nexus Repository (Internal Artifact Repository)
- Shared Library (`common-api`)

---

## 📚 Project Purpose

This project is designed as a learning laboratory to understand how modern distributed systems are built using the Spring ecosystem.

The project demonstrates several important architectural concepts:

- Service Discovery
- Centralized Configuration
- Shared Internal Libraries
- Synchronous Communication
- Asynchronous Communication
- Circuit Breaker & Fallback
- Artifact Management
- Event-Driven Architecture

---

## 🏗️ Architecture Overview

```text
                           ┌─────────────────────┐
                           │     Nexus Repo      │
                           │  Internal Library   │
                           └─────────┬───────────┘
                                     │
                                     │ common-api
                                     ▼
 ┌────────────────────────────────────────────────────────────────┐
 │                      Spring Boot Services                      │
 └────────────────────────────────────────────────────────────────┘

 ┌──────────────────┐        OpenFeign         ┌──────────────────┐
 │  order-service   │ ───────────────────────► │ product-service  │
 │      :8083       │                          │      :8082       │
 └────────┬─────────┘                          └──────────────────┘
          │
          │ StreamBridge
          ▼
 ┌──────────────────┐        RocketMQ          ┌──────────────────────┐
 │ order-created    │ ───────────────────────► │ notification-service │
 │      topic       │                          │        :8087         │
 └──────────────────┘                          └──────────────────────┘

          ▲                                             ▲
          │                                             │
          └─────────────────────────────────────────────┘
                           Nacos
                Service Discovery & Config Center
                         localhost:8848
````

---

## 📦 Project Structure

```text
microservices-demo/
│
├── docker-compose.yml
├── README.md
│
├── rocketmq/
│   └── broker.conf
│
├── common-api/
│   └── Shared DTOs, Events, Enums, Constants
│
├── product-service/
│   └── Product Provider Service
│
├── order-service/
│   └── Order Service (Feign + Sentinel + RocketMQ Producer)
│
└── notification-service/
    └── RocketMQ Consumer Service
```

---

## 🧩 Modules Description

### `common-api`

Shared internal library containing:

* DTOs
* Event objects
* Enums
* Constants

Published to Nexus and reused by all services.

### `product-service`

Provides product information via REST API.

### `order-service`

Main orchestration service that:

1. Calls `product-service` via OpenFeign.
2. Applies fallback using Sentinel.
3. Publishes `OrderCreatedEvent` to RocketMQ.

### `notification-service`

Consumes `OrderCreatedEvent` and simulates sending notifications.

---

## ⚙️ Infrastructure Components

### Nacos

Used for:

* Service Discovery
* Centralized Configuration

### Nexus Repository

Used to host internal Maven artifacts such as `common-api`.

### RocketMQ

Used as message broker for event-driven communication.

### Sentinel

Used for:

* Circuit Breaker
* Fallback
* Fault Tolerance

---

## 🚀 Technology Stack

| Technology           | Purpose                      |
| -------------------- | ---------------------------- |
| Spring Boot          | Application Framework        |
| Spring Cloud         | Distributed Systems Toolkit  |
| Spring Cloud Alibaba | Alibaba Cloud Integrations   |
| Nacos                | Discovery & Configuration    |
| OpenFeign            | Declarative HTTP Client      |
| Sentinel             | Fault Tolerance              |
| RocketMQ             | Messaging                    |
| Nexus Repository     | Artifact Repository          |
| Docker Compose       | Infrastructure Orchestration |
| Maven                | Build Tool                   |

---

## 🐳 Start Infrastructure

```bash
docker compose up -d
```

This will start:

* Nexus Repository
* Nacos Server
* RocketMQ Nameserver
* RocketMQ Broker

---

## 🌐 Service URLs

| Service              | URL                                                        |
| -------------------- | ---------------------------------------------------------- |
| Nexus Repository     | [http://localhost:8081](http://localhost:8081)             |
| Nacos Console        | [http://localhost:8848/nacos](http://localhost:8848/nacos) |
| Product Service      | [http://localhost:8082](http://localhost:8082)             |
| Order Service        | [http://localhost:8083](http://localhost:8083)             |
| Notification Service | [http://localhost:8087](http://localhost:8087)             |

---

## 🔑 Default Credentials

### Nexus Repository

* Username: `admin`
* Password: Check `/nexus-data/admin.password`

### Nacos

* Username: `nacos`
* Password: `nacos`

---

## 📦 Deploy Shared Library to Nexus

```bash
cd common-api
./mvnw clean deploy
```

This publishes the `common-api` artifact to Nexus so it can be consumed by all services.

---

## ▶️ Run Services

### Product Service

```bash
cd product-service
./mvnw spring-boot:run
```

### Order Service

```bash
cd order-service
./mvnw spring-boot:run
```

### Notification Service

```bash
cd notification-service
./mvnw spring-boot:run
```

---

## 🧪 API Testing

### Get Product

```bash
curl http://localhost:8082/products/1
```

### Create Order

```bash
curl -X POST http://localhost:8083/orders \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2
  }'
```

Expected response:

```text
Order created: 1
```

---

## 📩 Event Flow

1. Client sends request to `order-service`.
2. `order-service` calls `product-service` using OpenFeign.
3. Product stock is validated.
4. `OrderCreatedEvent` is published to RocketMQ.
5. `notification-service` consumes the event.
6. Notification is simulated.

---

## 🔄 Communication Patterns

### Synchronous Communication

* REST API
* OpenFeign
* Service Discovery via Nacos

### Asynchronous Communication

* Event-Driven Architecture
* RocketMQ Topics

---

## 🛡️ Fault Tolerance

`order-service` uses Sentinel fallback to handle failures when `product-service` is unavailable.

---

## 📁 Shared Library Workflow

```text
common-api
    └── mvn deploy
            ↓
         Nexus Repository
            ↓
    product-service
    order-service
    notification-service
```

---

## 🧠 Key Learning Topics

* Spring Cloud Fundamentals
* Service Discovery
* Centralized Configuration
* Internal Maven Repository
* Shared Libraries
* OpenFeign
* Circuit Breaker
* Event-Driven Architecture
* Distributed Systems Design

---

## 📝 License

This project is intended for educational and learning purposes.
