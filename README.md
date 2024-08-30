# 🌮 Taco House

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)

## Introduction

Taco House is a full-stack food ordering web application developed using Spring Boot 3.0, Spring MVC, Spring Security 6, and Thymeleaf. This project showcases a comprehensive set of features for both users and administrators, providing a seamless experience for ordering tacos online.

## Features

### User Features
- 👤 User registration and authentication (including OAuth)
- 🔐 Profile management
- 🌯 Menu browsing with pre-made and customizable tacos
- 🛒 Fully functional ordering system
- 💳 Integrated payment gateway
- ⭐ Review and rating system
- 📧 Email notifications for order updates
- 💬 Customer support tickets

### Admin Features
- 📊 Admin dashboard
- 👥 User management
- 📦 Order management
- 🌟 Review management
- 🍽️ Menu item management

### Additional Features
- 🌤️ Taco recommendations based on season/time
- 💬 Contact ticket system using Spring Cloud Kafka
- 📱 Responsive design with HTML/CSS
- 🐳 Docker deployment

## Technologies Used

- Java Spring Boot 3.0
- Spring MVC
- Spring Security 6
- Thymeleaf
- Spring Cloud
- Spring Data JPA
- MySQL Database
- Spring Cloud Kafka
- Jackson (for serialization)
- HTML/CSS
- Docker

## Project Structure

```
tacohouse/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── tacohouse/
│   │   │           ├── config/
│   │   │           ├── controller/
│   │   │           ├── model/
│   │   │           ├── repository/
│   │   │           ├── service/
│   │   │           └── TacohouseApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   └── js/
│   │       └── templates/
│   │           ├── admin/
│   │           └── user/
│   └── test/
├── pom.xml
├── Dockerfile
└── README.md
```

## Configuration

Here's the configuration for the Taco House application:

```properties
# H-2 DB Config
spring.datasource.generate-unique-name=false
spring.datasource.name=tacohouse

# JPA Config
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true

# Actuator Config
management.endpoints.web.exposure.include=*

# Admin Server Config
spring.boot.admin.client.url=http://localhost:9090
spring.boot.admin.client.username=${TACOHOUSE_USERNAME}
spring.boot.admin.client.password=${TACOHOUSE_PASSWORD}

# Kafka Config
spring.cloud.stream.kafka.binder.brokers=localhost:9092
spring.cloud.stream.output-bindings=contact
spring.cloud.stream.bindings.contact.destination=tacohouse
spring.cloud.function.definition=consumer
spring.cloud.stream.bindings.consumer-in-0.destination=tacohouse
```

## Installation

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/tacohouse.git
   ```
2. Navigate to the project directory:
   ```
   cd tacohouse
   ```
3. Build the project:
   ```
   mvn clean install
   ```
4. Run the Docker container:
   ```
   docker build -t tacohouse .
   docker run -p 8080:8080 tacohouse
   ```

## Usage

After starting the application, you can access it at `http://localhost:8080`. Use the following credentials for admin access:

- Username: `${TACOHOUSE_USERNAME}`
- Password: `${TACOHOUSE_PASSWORD}`

> **Note:** Replace `${TACOHOUSE_USERNAME}` and `${TACOHOUSE_PASSWORD}` with actual environment variables or values.

## API Documentation

API documentation is available at `http://localhost:8080/swagger-ui.html` when the application is running.

## Contributing

I welcome contributions to the Taco House project! Please feel free to submit issues, fork the repository and send pull requests!


---

Created with 🌮 by Ansh