# 🎬 Movwe – Spring Boot Backend

**Movwe** is a personal movie tracking application where users can manually log the movies, series, and documentaries they’ve watched.  Movwe focuses on user-generated content – allowing people to write and manage their own watch history.

This repository contains the **backend** developed with **Spring Boot**.

---

## 🚀 Features

- 🔐 User registration and login using JWT authentication
- 📝 Create and manage personal entries:
    - Movies
    - TV Shows
    - Documentaries
- ⭐ Add personal notes and ratings
- 📂 Filter and categorize entries by type and date

---

## ⚙️ Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- PostgreSQL
- RESTful API design

---

## 📦 Getting Started

### ✅ Prerequisites

- Java 17+
- Maven
- PostgreSQL running locally or via Docker

### 🔧 Configuration

Set up your `application.properties`:

```properties
# ===============================
spring.application.name=movwe-backend
server.port=9999

# ===============================
# =  JPA / Hibernate Config     =
# ===============================
#spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
#spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
#spring.jpa.properties.hibernate.format_sql=true

# ===============================
# =  PostgreSQL Config          =
# ===============================
spring.datasource.url=jdbc:postgresql://localhost:5432/movwe
spring.datasource.username={your_username}
spring.datasource.password={your_password}
spring.datasource.driver-class-name=org.postgresql.Driver

# ===============================
# = MongoDB Configuration      =
# ===============================
spring.data.mongodb.uri=mongodb://localhost:27017/movwe
spring.data.mongodb.username={your_username}
spring.data.mongodb.password={your_password}
```

## 🧱 Project Structure

```graphql
movwe-backend/
├── config/                  # JWT & security configuration
├── controller/              # REST API controllers
├── dto/                     # Data transfer objects
├── model/                   # JPA entities (User, Entry)
├── repository/              # Spring Data JPA repositories
├── security/                # JWT filters and utilities
├── service/                 # Business logic
├── MovweBackendApplication.java
└── resources/
└── application.properties
```

---

## 📄 License

This project is licensed under the MIT License.


