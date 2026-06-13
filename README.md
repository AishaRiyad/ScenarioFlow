# ScenarioFlow 🌷

## Interactive Decision-Based Simulation Platform

ScenarioFlow is a full-stack interactive decision-based simulation platform where admins create realistic decision-based scenarios, and users play them by making choices that lead to different paths, endings, scores, and feedback.

---

## Features

* User registration and login
* JWT authentication
* Role-based authorization
* Admin and User roles
* Scenario creation
* Scenario publishing
* Decision tree builder using nodes and choices
* Interactive scenario player
* Attempt tracking
* Result evaluation
* User attempt history
* Responsive React UI

---

## Tech Stack

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* MySQL
* JWT Authentication
* Lombok
* Maven

### Frontend

* React
* Vite
* React Router
* Axios
* CSS

---

## Project Structure

```bash
scenarioflow/
├── backend/
│   ├── src/main/java/com/scenarioflow/backend/
│   │   ├── auth/
│   │   ├── config/
│   │   ├── user/
│   │   ├── scenario/
│   │   ├── node/
│   │   ├── choice/
│   │   ├── attempt/
│   │   └── common/
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── api/
│   │   ├── components/
│   │   └── pages/
│   └── package.json
│
├── docs/
└── README.md
```

---

## Database Setup

### Create Database

```sql
CREATE DATABASE scenarioflow;
```

---

## Backend Configuration

Update:

```text
backend/src/main/resources/application.yml
```

Example:

```yaml
server:
  port: 8083

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/scenarioflow
    username: root
    password: YOUR_PASSWORD

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

jwt:
  secret: your-very-long-secret-key-that-is-at-least-32-characters
  expiration: 86400000
```

---

## Running the Backend

```bash
cd backend
mvnw.cmd spring-boot:run
```

### Backend URL

```text
http://localhost:8083
```

---

## Running the Frontend

```bash
cd frontend
npm install
npm run dev
```

### Frontend URL

```text
http://localhost:5173
```

---

## User Roles

### 👤 USER

A regular registered account.

#### Permissions

* View published scenarios
* Play scenarios
* Submit choices
* View results
* View attempt history

---

### 🛠️ ADMIN

Administrator account with advanced permissions.

#### Permissions

* Create scenarios
* Publish scenarios
* Create nodes
* Create choices
* Build decision trees

---

## Promote a User to Admin

By default, every registered account is assigned the **USER** role.

To make a user an administrator:

```sql
UPDATE users
SET role = 'ADMIN'
WHERE email = 'your-email@example.com';
```

> After updating the role, logout and login again to receive a new JWT token with admin privileges.

---

## API Authentication

The application uses JWT (JSON Web Tokens) for authentication.

After successful login:

1. Backend generates a JWT token.
2. Frontend stores the token.
3. Every protected request includes:

```http
Authorization: Bearer <token>
```

---

# API Testing with Postman

## Base URL

```text
http://localhost:8083/api
```

### Headers

For protected endpoints:

```http
Authorization: Bearer YOUR_TOKEN
Content-Type: application/json
```

> You receive the JWT token from the Login or Register response.

---

## Postman Testing Flow

Follow this order to test the entire application:

1. Register User
2. Login User
3. Promote User to ADMIN
4. Login Again
5. Create Scenario
6. Create START Node
7. Create DECISION Node
8. Create END Node
9. Create Choice (START → DECISION)
10. Create Choice (DECISION → END)
11. Publish Scenario
12. Get Published Scenarios
13. Start Attempt
14. Submit First Choice
15. Submit Second Choice
16. Get Result
17. Get User Attempts

---

## Common Errors

### 403 Forbidden

Possible reasons:

* USER token accessing ADMIN endpoint
* Missing token
* Expired token
* Token generated before role change

#### Solution

Login again and obtain a new JWT token.

---

### Could Not Load Result

Possible reasons:

* Attempt not completed
* Invalid attempt ID
* Missing Authorization header
* Security configuration issue

Verify:

```java
.requestMatchers("/api/attempts/**").authenticated()
```

---

### Empty Scenario List

Possible reasons:

* Scenario still in DRAFT status

Solution:

```sql
UPDATE scenarios
SET status = 'PUBLISHED'
WHERE id = 1;
```

---

### No Choices Appear

Possible reasons:

* Choices not created
* Incorrect node ID
* Incorrect nextNodeId

Verify:

```sql
SELECT * FROM choices;
```

---

## Frontend Pages

| Route                         | Description          |
| ----------------------------- | -------------------- |
| `/`                           | Landing Page         |
| `/login`                      | Login Page           |
| `/register`                   | Register Page        |
| `/scenarios`                  | Published Scenarios  |
| `/scenarios/:scenarioId/play` | Play Scenario        |
| `/attempts/:attemptId/result` | Result Page          |
| `/my-attempts`                | User Attempt History |
| `/admin`                      | Admin Dashboard      |
| `/admin/builder`              | Scenario Builder     |

---

## Future Improvements

* Drag-and-drop decision tree builder
* Scenario categories and filtering
* Scenario search functionality
* Scenario ratings and reviews
* AI-generated feedback
* AI-generated scenarios
* Analytics dashboard
* Scenario templates
* Docker deployment
* Public demo version

---

## Author

Developed by **Aisha Riyad Abu Jaab** as a personal learning project to practice full-stack web development using Spring Boot, React, JWT Authentication, and MySQL.

---

## License

This project is intended for educational and academic purposes.
