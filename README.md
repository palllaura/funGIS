# funGIS
A mushroom spot registry

## Features

* View a map with mushroom locations
* Add new location with commentary
* Edit an existing location commentary
* Delete a location
* Load initial data from a .json (?) file
* Uses a PostgreSQL database
* Frontend built with React
* OpenStreetMap
* Backend built with Java Spring Boot

## Prerequisites
* Java 17+
* Node.js + npm
* A package manager like Gradle
* A code editor like IntelliJ IDEA


## Installation
### Backend (Spring Boot)
1. Clone the repository:
   ```bash
   git clone https://github.com/palllaura/funGIS.git
   cd fungis

2. Open the project in your IDE (e.g., IntelliJ).

3. Build and run the backend:
   ```bash
   ./gradlew bootRun
4. The backend server will start at:
   http://localhost:8080

### Frontend (React)
1. Navigate to the frontend folder:
   ```bash
   cd frontend
2. Install dependencies:
   ```bash
   npm install
3. Start the development server:
   ```bash
   npm run dev
4. The frontend will be available at:
   http://localhost:5173


