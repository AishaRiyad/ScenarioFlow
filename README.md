# ScenarioFlow 🌷

## Interactive Decision-Based Simulation Platform with Analytics, Achievements, Ratings, and Visual Builder

ScenarioFlow is a full-stack interactive decision-based simulation platform where admins create realistic decision-based scenarios, and users play them by making choices that lead to different paths, endings, scores, feedback, achievements, and ratings.

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

### Advanced Features

* Visual decision tree builder
* Save node positions in visual builder
* Smart personalized feedback
* Community comments system
* Scenario rating system
* Achievement system
* Analytics dashboard
* PDF result reports
* User profile management
* Scenario search and filtering


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
│   │   ├── attempt/
│   │   ├── auth/
│   │   ├── choice/
│   │   ├── comment/
│   │   ├── common/
│   │   ├── config/
│   │   ├── dashboard/
│   │   ├── evaluation/
│   │   ├── node/
│   │   ├── rating/
│   │   ├── scenario/
│   │   ├── user/
│   │   └── BackendApplication.java
│   │
│   ├── src/main/resources/
│   │   └── application.yml
│   │
│   ├── database/
│   │   └── schema.sql
│   │
│   ├── Dockerfile
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── api/
│   │   │   └── api.js
│   │   │
│   │   ├── assets/
│   │   │
│   │   ├── components/
│   │   │   └── common/
│   │   │       ├── Navbar.jsx
│   │   │       └── Navbar.css
│   │   │
│   │   ├── pages/
│   │   │   ├── admin/
│   │   │   ├── auth/
│   │   │   ├── landing/
│   │   │   └── user/
│   │   │
│   │   ├── App.jsx
│   │   ├── main.jsx
│   │   └── index.css
│   │
│   ├── Dockerfile
│   └── package.json
│
├── docs/
│   ├── requirements.md
│   ├── system-analysis.md
│   ├── database-design.md
│   └── sql-schema.md
│
├── docker-compose.yml
└── README.md
```

### Frontend Pages Structure

```bash
src/pages/
│
├── admin/
│   ├── AdminDashboard.jsx
│   ├── AdminDashboard.css
│   ├── ScenarioBuilderPage.jsx
│   ├── ScenarioBuilderPage.css
│   ├── VisualBuilderPage.jsx
│   └── VisualBuilderPage.css
│
├── auth/
│   ├── LoginPage.jsx
│   ├── RegisterPage.jsx
│   └── Auth.css
│
├── landing/
│   ├── LandingPage.jsx
│   └── LandingPage.css
│
└── user/
    ├── ScenarioListPage.jsx
    ├── ScenarioListPage.css
    ├── ScenarioDetailsPage.jsx
    ├── ScenarioDetailsPage.css
    ├── ScenarioPlayPage.jsx
    ├── ScenarioPlayPage.css
    ├── ResultPage.jsx
    ├── ResultPage.css
    ├── MyAttemptsPage.jsx
    ├── MyAttemptsPage.css
    ├── ProfilePage.jsx
    ├── ProfilePage.css
    ├── LeaderboardPage.jsx
    └── LeaderboardPage.css
```

---


## Database Entities

* User
* Scenario
* ScenarioNode
* Choice
* Attempt
* AttemptStep
* ScenarioRating
* ScenarioComment

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
* View scenario details
* Play scenarios
* Submit choices
* View results
* View attempt history
* View personal profile
* Update full name, email, and password
* Unlock achievements based on progress
* Search scenarios
* Filter scenarios by category
* Add comments to scenarios
* Rate scenarios
* Download PDF reports
* Receive personalized feedback
* Manage profile information
* View leaderboard
* Receive scenario recommendations

---

### 🛠️ ADMIN

Administrator account with advanced permissions.

#### Permissions

* Create scenarios
* Publish scenarios
* Create nodes
* Create choices
* Build decision trees
* View dashboard statistics
* Track total users, scenarios, attempts, completed attempts, and average score
* Manage scenarios from dashboard
* Publish scenarios directly from dashboard
* Use improved builder workflow with dropdown selection and node preview
* Visual scenario builder
* Save visual layout positions
* View platform analytics
* Unpublish scenarios
* Delete scenarios
* Clone scenarios
* Validate scenarios before publishing
* Create scenarios from templates

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

---

## Get User Profile

```http
GET http://localhost:8083/api/users/me
```

---

## Update User Profile

```http
PUT http://localhost:8083/api/users/me
```

---

## Get User Achievements

```http
GET http://localhost:8083/api/users/me/achievements
```

---

## Get Admin Dashboard Stats

```http
GET http://localhost:8083/api/dashboard/stats
```

---

## Rate Scenario

```http
POST http://localhost:8083/api/ratings
```

---

## Get Scenario Rating Summary

```http
GET http://localhost:8083/api/ratings/scenario/{scenarioId}
```

---

## Add Comment

```http
POST http://localhost:8083/api/comments
```

---

## Get Scenario Comments

```http
GET http://localhost:8083/api/comments/scenario/{scenarioId}
```

---

## Update Visual Builder Node Position

```http
PATCH http://localhost:8083/api/nodes/{nodeId}/position
```

---

## Search Published Scenarios

```http
GET http://localhost:8083/api/scenarios/published?keyword=Interview
```

---

## Filter Published Scenarios

```http
GET http://localhost:8083/api/scenarios/published?category=Career
```

---

## Create Scenario From Template

```http
POST http://localhost:8083/api/scenarios/template
```

Body:

```json
{
  "templateType": "JOB_INTERVIEW",
  "customTitle": "Junior Developer Interview"
}
```

Available Templates:

* JOB_INTERVIEW
* CUSTOMER_SERVICE
* LEADERSHIP
* ETHICS
* CONFLICT_RESOLUTION

---

## Clone Scenario

```http
POST http://localhost:8083/api/scenarios/{scenarioId}/clone
```

Example:

```http
POST http://localhost:8083/api/scenarios/1/clone
```

Response:

```json
{
  "id": 7,
  "title": "Leadership Scenario Copy",
  "status": "DRAFT"
}
```

---

## Validate Scenario

```http
GET http://localhost:8083/api/scenarios/{scenarioId}/validate
```

Valid Response:

```json
{
  "valid": true,
  "errors": []
}
```

Invalid Response:

```json
{
  "valid": false,
  "errors": [
    "Scenario must have one START node.",
    "Scenario must have at least one END node."
  ]
}
```

---

## Publish Scenario

```http
PATCH http://localhost:8083/api/scenarios/{scenarioId}/publish
```

---

## Unpublish Scenario

```http
PATCH http://localhost:8083/api/scenarios/{scenarioId}/unpublish
```

---

## Swagger Documentation

Swagger UI:

```http
GET http://localhost:8083/swagger-ui.html
```

OpenAPI JSON:

```http
GET http://localhost:8083/v3/api-docs
```

---




## Build and Run

docker compose up --build

Frontend:
http://localhost:5173

Backend:
http://localhost:8083

Swagger:
http://localhost:8083/swagger-ui.html


## Postman Testing Flow

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
11. Validate Scenario
12. Publish Scenario
13. Clone Scenario (Optional)
14. Get Published Scenarios
15. Search Published Scenarios
16. Filter Published Scenarios by Category
17. View Scenario Details
18. Start Attempt
19. Submit First Choice
20. Submit Second Choice
21. Get Result
22. Receive Personalized Feedback
23. Download PDF Report
24. Get User Attempts
25. Get User Profile
26. Update User Profile
27. Get User Achievements
28. Submit Scenario Rating
29. Get Scenario Rating Summary
30. Submit Scenario Comment
31. Get Scenario Comments
32. Get Dashboard Statistics
33. Save Visual Builder Node Position
34. Create Scenario From Template
35. Unpublish Scenario (Optional)
36. View Leaderboard
37. Get Scenario Recommendations
38. Open Swagger Documentation

---


## Example User Journey

1. Register
2. Login
3. Browse scenarios
4. View scenario details
5. Read ratings and comments
6. Start scenario
7. Make decisions
8. Reach ending
9. Receive personalized feedback
10. Download PDF report
11. Rate scenario
12. Add comment
13. Unlock achievements
14. View profile statistics
15. Receive recommendations
16. View leaderboard

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

| Route                         | Description                                                 |
| ----------------------------- | ----------------------------------------------------------- |
| `/`                           | Landing Page                                                |
| `/login`                      | Login Page                                                  |
| `/register`                   | Register Page                                               |
| `/profile`                    | User profile, achievements, and account management          |
| `/scenarios`                  | Scenario list, search, filtering, ratings, and comments     |
| `/scenarios/:scenarioId`      | Scenario details page with ratings and community comments   |
| `/scenarios/:scenarioId/play` | Interactive scenario player                                 |
| `/attempts/:attemptId/result` | Result page, personalized feedback, rating, and PDF reports |
| `/my-attempts`                | User attempt history and performance tracking               |
| `/leaderboard`                | Global leaderboard                                          |
| `/admin`                      | Analytics dashboard and scenario management                 |
| `/admin/builder`              | Form-based scenario builder                                 |
| `/admin/visual-builder`       | Visual decision tree builder with node position persistence |

---






## Future Enhancements

* Full drag-and-drop node creation
* Connect nodes visually without forms
* OpenAI-powered feedback generation
* Scenario templates marketplace
* Public user profiles
* Email notifications
* Advanced analytics charts
* Cloud deployment

---


## Why This Project Is Special

ScenarioFlow is not a traditional CRUD application.

The project combines:

* Authentication and authorization
* Decision-tree engine
* Interactive simulations
* User progress tracking
* Achievement system
* Community engagement through comments and ratings
* Visual scenario mapping
* Analytics dashboard
* Smart personalized feedback
* PDF report generation

This makes ScenarioFlow a portfolio-level software engineering project that demonstrates backend architecture, frontend development, database design, system modeling, and user experience design.



## Deployment Ready Features

* Dockerized backend
* Dockerized frontend
* Docker Compose orchestration
* Swagger API documentation
* JWT authentication
* MySQL persistence
* Environment variable support


## Author

Developed by **Aesha Reyad Abu Jeeb** as a personal learning project to practice full-stack web development using Spring Boot, React, JWT Authentication, MySQL, Analytics Dashboards, Achievements, and Scenario Ratings.

---

## License

This project is intended for educational and academic purposes.
