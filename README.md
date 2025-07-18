# funGIS
A web application for registering and managing mushroom locations in Estonia.

## Features

* Interactive map with [OpenStreetMap](https://www.openstreetmap.org/)
* View mushroom locations
* Add new location with a description
* Edit or delete existing locations (currently only on backend)
* Load initial data from an SQL file
* Backend built with Java + Spring Boot
* Frontend built with React
* Uses PostgreSQL as the database

## Prerequisites
* Java 17+
* Node.js + npm
* Gradle (or use the included `./gradlew`)
* An IDE or code editor (e.g., IntelliJ IDEA)
* Docker


## Installation

### Database (PostgreSQL + PostGIS via Docker)
1. Download PostGIS image:
   ```bash
   docker pull postgis/postgis:17-master 
2. Run the container:
   ```bash
   docker run -d -e POSTGRES_USER=fungis -e POSTGRES_PASSWORD=fungis -p 5432:5432 --name postgis-db postgis/postgis:17-master

### Backend (Spring Boot)
1. Clone the repository:
   ```bash
   git clone https://github.com/palllaura/funGIS.git
   cd fungis

2. Open the project in your IDE.

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
   
<img width="1680" height="1050" alt="Screenshot 2025-07-18 at 17 28 31" src="https://github.com/user-attachments/assets/8ac3c841-7019-413a-aacc-e8df1641e477" />
