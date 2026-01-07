# Sneaker Inventory - Spring Boot API

A full-stack sneaker inventory management system with PostgreSQL database, Spring Boot REST API, and JWT authentication.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Database Setup](#database-setup)
- [Application Setup](#application-setup)
- [Running the Application](#running-the-application)
- [Authentication](#authentication)
- [Testing the API](#testing-the-api)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Troubleshooting](#troubleshooting)

---

## Prerequisites

Make sure you have the following installed:

- **Java 17 or 21** - Check with `java -version`
- **Gradle** - Included via Gradle wrapper (`./gradlew`)
- **PostgreSQL 15** - Installed via Homebrew
- **Homebrew** (Mac) - For PostgreSQL installation

---

## Database Setup

### 1. Install PostgreSQL (if not already installed)
```bash
# Install PostgreSQL via Homebrew
brew install postgresql@15

# Start PostgreSQL service
brew services start postgresql@15

# Add PostgreSQL to PATH (if not already done)
export PATH="/opt/homebrew/opt/postgresql@15/bin:$PATH"

# Make PATH change permanent
echo 'export PATH="/opt/homebrew/opt/postgresql@15/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

### 2. Create the Database
```bash
# Create the sneaker_inventory database
createdb sneaker_inventory

# Verify database was created
psql -l | grep sneaker_inventory
```

### 3. Verify Database Connection
```bash
# Connect to the database
psql sneaker_inventory

# You should see the PostgreSQL prompt:
# sneaker_inventory=#

# Exit with:
\q
```

---

## Application Setup

### 1. Clone/Navigate to Project Directory
```bash
cd /path/to/sneaker-inventory
```

### 2. Verify Configuration

Check that `src/main/resources/application.properties` contains:
```properties
# Database Connection
spring.datasource.url=jdbc:postgresql://localhost:5432/sneaker_inventory
spring.datasource.username=rumble
spring.datasource.password=

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server Port
server.port=8080

# JWT Configuration
jwt.secret=MyVerySecretKeyForJWTTokenGenerationThatIsAtLeast256BitsLong12345
jwt.expiration=86400000
```

**Note:** Replace `rumble` with your Mac username if different. Find it with `whoami`.

### 3. Build the Project (Optional)
```bash
# Clean and build the project
./gradlew clean build
```

---

## Running the Application

### Start PostgreSQL (if not already running)
```bash
# Check if PostgreSQL is running
brew services list | grep postgresql

# Start if stopped
brew services start postgresql@15
```

### Start the Spring Boot Application
```bash
# Run the application
./gradlew bootRun
```

### Verify Application Started

You should see output like:
```
Tomcat started on port 8080 (http) with context path '/'
Started SneakerInventoryApplication in X.XXX seconds
```

**Application is now running at:** `http://localhost:8080`

---

## Authentication (Session 1 & 2 - Dec 2025)

### ✅ Completed Features

**User Registration & Login:**
- User entity with BCrypt password encryption
- Username/email uniqueness validation
- JWT token generation on successful login
- Secure password verification

**JWT Token Authentication:**
- 24-hour token expiration
- Custom claims (role: USER/ADMIN)
- HS256 signature algorithm
- Stateless authentication pattern

### Authentication Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| **POST** | `/api/auth/register` | Register new user | RegisterRequest JSON |
| **POST** | `/api/auth/login` | Login and get JWT token | LoginRequest JSON |

### Register Request Format
```json
{
  "username": "bryant",
  "email": "bryant@sneakers.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "message": "User registered successfully",
  "username": "bryant"
}
```

### Login Request Format
```json
{
  "username": "bryant",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInN1YiI6ImJyeWFudCIsImlhdCI6MTczMzg3MjQwMCwiZXhwIjoxNzMzOTU4ODAwfQ.xK7Y9L5nP3mR2tQ8vW6zE4sH1jN0pO9qA2bC3dD4eF5",
  "username": "bryant"
}
```

### Testing Authentication

**Register a new user:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "bryant",
    "email": "bryant@sneakers.com",
    "password": "password123"
  }'
```

**Login and get JWT token:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "bryant",
    "password": "password123"
  }'
```

**Decode your JWT token at:** https://jwt.io/

---

## Testing the API

### Using curl

Open a **new terminal window** (keep the app running in the first one).

#### 1. Create a Sneaker (POST)
```bash
curl -X POST http://localhost:8080/api/sneakers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Air Jordan 1 Retro High",
    "modelNumber": "555088-134",
    "size": 10.5,
    "condition": "DS"
  }'
```

**Expected Response:**
```json
{"id":1,"name":"Air Jordan 1 Retro High","modelNumber":"555088-134","size":10.5,"condition":"DS"}
```

#### 2. Get All Sneakers (GET)
```bash
curl http://localhost:8080/api/sneakers
```

**Expected Response:**
```json
[{"id":1,"name":"Air Jordan 1 Retro High","modelNumber":"555088-134","size":10.5,"condition":"DS"}]
```

#### 3. Get Single Sneaker (GET by ID)
```bash
curl http://localhost:8080/api/sneakers/1
```

#### 4. Update Sneaker (PUT)
```bash
curl -X PUT http://localhost:8080/api/sneakers/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Air Jordan 1 Retro High",
    "modelNumber": "555088-134",
    "size": 10.5,
    "condition": "LIGHTLY_WORN"
  }'
```

#### 5. Delete Sneaker (DELETE)
```bash
curl -X DELETE http://localhost:8080/api/sneakers/1
```

---

## Project Structure
```
sneaker-inventory/
├── src/
│   ├── main/
│   │   ├── java/com/sneakers/sneaker_inventory/
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java      # Spring Security configuration
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java      # Authentication endpoints
│   │   │   │   └── SneakerController.java   # REST API endpoints
│   │   │   ├── dto/
│   │   │   │   ├── AuthResponse.java        # Auth response format
│   │   │   │   ├── LoginRequest.java        # Login request format
│   │   │   │   └── RegisterRequest.java     # Registration request format
│   │   │   ├── model/
│   │   │   │   ├── Condition.java           # Enum (DS, VNDS, etc.)
│   │   │   │   ├── Role.java                # Enum (USER, ADMIN)
│   │   │   │   ├── Sneaker.java             # Entity (database table)
│   │   │   │   └── User.java                # User entity with BCrypt password
│   │   │   ├── repository/
│   │   │   │   ├── SneakerRepository.java   # Database operations
│   │   │   │   └── UserRepository.java      # User database operations
│   │   │   ├── service/
│   │   │   │   ├── AuthService.java         # Authentication business logic
│   │   │   │   └── SneakerService.java      # Business logic
│   │   │   ├── util/
│   │   │   │   └── JwtUtil.java             # JWT token generation/validation
│   │   │   └── SneakerInventoryApplication.java # Main application
│   │   └── resources/
│   │       └── application.properties       # Configuration
│   └── test/                                # Test files
├── build.gradle                             # Dependencies & build config
└── README.md                                # This file
```

---

## API Endpoints

### Authentication Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| **POST** | `/api/auth/register` | Register new user | RegisterRequest JSON |
| **POST** | `/api/auth/login` | Login and get JWT token | LoginRequest JSON |

### Sneaker Endpoints (Coming: JWT protection)

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| **POST** | `/api/sneakers` | Create a new sneaker | Sneaker JSON |
| **GET** | `/api/sneakers` | Get all sneakers | None |
| **GET** | `/api/sneakers/{id}` | Get sneaker by ID | None |
| **PUT** | `/api/sneakers/{id}` | Update sneaker | Sneaker JSON |
| **DELETE** | `/api/sneakers/{id}` | Delete sneaker | None |

### Sneaker JSON Format
```json
{
  "name": "Air Jordan 1 Retro High",
  "modelNumber": "555088-134",
  "size": 10.5,
  "condition": "DS"
}
```

### Valid Condition Values

- `DS` - Deadstock (brand new)
- `VNDS` - Very Near Deadstock
- `LIGHTLY_WORN`
- `WORN`
- `HEAVILY_WORN`

---

## Viewing Database Data

### Option 1: Command Line (psql)
```bash
# Connect to database
psql sneaker_inventory

# View all sneakers
SELECT * FROM sneakers;

# View all users (passwords are encrypted with BCrypt)
SELECT id, username, email, role FROM users;

# View table structures
\d sneakers
\d users

# Exit
\q
```

### Option 2: DBeaver (GUI - Recommended)

1. Download: https://dbeaver.io/download/
2. Open DBeaver
3. Click "New Database Connection"
4. Select "PostgreSQL"
5. Configure:
    - **Host:** `localhost`
    - **Port:** `5432`
    - **Database:** `sneaker_inventory`
    - **Username:** `rumble` (your Mac username)
    - **Password:** (leave empty)
6. Click "Test Connection" then "Finish"
7. Browse `sneaker_inventory` → `public` → `Tables` → `sneakers` or `users`

---

## Stopping the Application

### Stop Spring Boot

In the terminal where `./gradlew bootRun` is running:
- Press `Ctrl + C`

### Stop PostgreSQL (if needed)
```bash
# Stop PostgreSQL service
brew services stop postgresql@15
```

---

## Troubleshooting

### Error: "role 'postgres' does not exist"

**Solution:** Update `application.properties` username to your Mac username:
```properties
spring.datasource.username=rumble  # Change to your username (run `whoami`)
```

### Error: "database 'sneaker_inventory' does not exist"

**Solution:** Create the database:
```bash
createdb sneaker_inventory
```

### Error: "command not found: createdb"

**Solution:** PostgreSQL bin directory not in PATH:
```bash
export PATH="/opt/homebrew/opt/postgresql@15/bin:$PATH"
echo 'export PATH="/opt/homebrew/opt/postgresql@15/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

### Error: Port 8080 already in use

**Solution:** Kill the process using port 8080:
```bash
# Find process using port 8080
lsof -ti:8080

# Kill the process (replace PID with number from above)
kill -9 <PID>
```

Or change the port in `application.properties`:
```properties
server.port=8081
```

### Application won't start - Gradle errors

**Solution:** Clean and rebuild:
```bash
./gradlew clean build --refresh-dependencies
```

---

## Tech Stack

- **Backend:** Spring Boot 3.4.12
- **Build Tool:** Gradle
- **Database:** PostgreSQL 15
- **ORM:** Hibernate/JPA
- **Java Version:** 21
- **Authentication:** JWT with BCrypt password encryption
- **Security:** Spring Security 6.x

---

## Next Steps

**Session 3 (Next):**
- [ ] Implement JWT token validation filter
- [ ] Secure sneaker endpoints (require JWT for POST/PUT/DELETE)
- [ ] Add role-based authorization (@PreAuthorize)
- [ ] Test protected endpoints with JWT tokens

**Future Sessions:**
- [ ] Add `imageUrl` field to store sneaker images
- [ ] Build Angular frontend to display sneakers
- [ ] Add search/filter functionality
- [ ] Implement image upload (Cloudinary/AWS S3)
- [ ] Deploy to production

---

## Architecture Pattern

This application follows the standard 4-layer Spring Boot architecture:
```
Controller Layer  (HTTP requests/responses)
      ↓
Service Layer     (Business logic + JWT generation)
      ↓
Repository Layer  (Database operations)
      ↓
Database Layer    (PostgreSQL)
```

**Authentication Flow:**
```
Login Request
    ↓
AuthController
    ↓
AuthService (verify password with BCrypt)
    ↓
JwtUtil (generate JWT token)
    ↓
Return token to client
    ↓
Client sends token with future requests (Authorization: Bearer <token>)
```

---

## Additional Resources

- **Spring Boot Docs:** https://spring.io/guides
- **PostgreSQL Docs:** https://www.postgresql.org/docs/
- **Gradle Docs:** https://docs.gradle.org/
- **JWT.io:** https://jwt.io/ (decode and verify JWT tokens)
- **Spring Security Docs:** https://spring.io/projects/spring-security

---

## Session Log

**Session 0 (Dec 1, 2025):** PostgreSQL setup + Basic CRUD  
**Session 1 (Dec 3, 2025):** User Registration + Password Encryption  
**Session 2 (Jan 6, 2026):** Login + JWT Token Generation  
**Session 3 (Upcoming):** Token Validation + Protected Endpoints

---

## Author

Built as a learning project for Ford Motor Company backend development patterns.

**Created:** December 1, 2025  
**Last Updated:** January 6, 2026