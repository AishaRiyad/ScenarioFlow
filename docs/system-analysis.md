# System Analysis – ScenarioFlow

## 1. User Roles

### Admin
- Create, update, and delete scenarios
- Build decision trees (nodes and choices)
- Publish/unpublish scenarios
- View statistics and reports

### User
- Register and login
- Browse available scenarios
- Play scenarios
- Make decisions
- View results and feedback



## 2. System Modules

### Authentication Module
Handles user registration, login, and role-based access.

### Scenario Management Module
Allows admins to create, update, delete, and publish scenarios.

### Decision Tree Module
Manages nodes and choices, and links between them.

### Scenario Player Module
Handles user interaction with scenarios and navigation between nodes.

### Attempt Tracking Module
Stores user progress and decisions during scenario play.

### Evaluation Module
Calculates scores and provides feedback based on user choices.

### Dashboard Module
Displays statistics and reports for admins.



## 3. Use Cases

### Admin
- Create a new scenario
- Add nodes and choices
- Link nodes together
- Publish scenario
- View reports

### User
- Register account
- Login
- View scenarios
- Start a scenario
- Make decisions
- View final result



## 4. User Flow

### Admin Flow
Login → Dashboard → Create Scenario → Add Nodes → Add Choices → Link Nodes → Publish

### User Flow
Register/Login → Browse Scenarios → Start Scenario → Make Decisions → Reach Ending → View Result



## 5. MVP Scope

### Included
- Authentication (login/register)
- Scenario CRUD
- Node and choice creation
- Scenario playing
- Basic evaluation
- Attempt tracking

### Excluded (for now)
- AI features
- Drag-and-drop builder
- Advanced analytics
- Gamification



