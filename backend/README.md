# Resource Labor Forecast Hours - Backend

A Spring Boot REST API application for managing resources and their forecasted labor hours.

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Run the Application

**Option 1: Using the quick start script (Recommended)**
```bash
cd backend
./run.sh          # macOS/Linux
run.bat           # Windows
```

**Option 2: Manual commands**
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

**Option 3: First time setup**
If you don't have Java or Maven installed, see [SETUP.md](SETUP.md) for detailed installation instructions.

### Verify It's Running
- Application: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
- Test API: `curl http://localhost:8080/api/resources`

### 📚 Documentation
- **[SETUP.md](SETUP.md)** - Complete installation and setup guide
- **[API-TESTING-GUIDE.md](API-TESTING-GUIDE.md)** - Comprehensive API testing examples
- **[PROJECT-SUMMARY.md](PROJECT-SUMMARY.md)** - Project overview and status

---

## Project Structure

```
backend/
├── src/main/java/com/forecast/
│   ├── ForecastApplication.java          # Main application class
│   ├── config/
│   │   └── CorsConfig.java               # CORS configuration
│   ├── controller/
│   │   ├── ResourceController.java       # Resource REST endpoints
│   │   └── ForecastController.java       # Forecast REST endpoints
│   ├── model/
│   │   ├── Resource.java                 # Resource entity
│   │   └── Forecast.java                 # Forecast entity
│   ├── repository/
│   │   ├── ResourceRepository.java       # Resource data access
│   │   └── ForecastRepository.java       # Forecast data access
│   └── service/
│       ├── ResourceService.java          # Resource business logic
│       └── ForecastService.java          # Forecast business logic
├── src/main/resources/
│   ├── application.properties            # Application configuration
│   └── schema.sql                        # Database schema
└── pom.xml                               # Maven dependencies
```

## Technologies Used

- **Java 17**
- **Spring Boot 3.1.5**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **H2 Database** (in-memory for development)
- **Lombok** (reduce boilerplate code)
- **Maven** (build tool)

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Getting Started

### 1. Build the Project

```bash
cd backend
mvn clean install
```

### 2. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 3. Access H2 Console (Development)

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:forecastdb`
- Username: `sa`
- Password: (leave empty)

## API Endpoints

### Resources API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/resources` | Get all resources |
| GET | `/api/resources/{id}` | Get resource by ID |
| POST | `/api/resources` | Create new resource |
| PUT | `/api/resources/{id}` | Update resource |
| DELETE | `/api/resources/{id}` | Delete resource |

#### Resource JSON Structure
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "department": "Engineering",
  "createdAt": "2026-06-14T10:30:00",
  "updatedAt": "2026-06-14T10:30:00"
}
```

### Forecasts API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/forecasts` | Get all forecasts |
| GET | `/api/forecasts/{id}` | Get forecast by ID |
| GET | `/api/forecasts/resource/{resourceId}` | Get forecasts by resource |
| GET | `/api/forecasts/week/{weekEndingDate}` | Get forecasts by week |
| POST | `/api/forecasts` | Create new forecast |
| PUT | `/api/forecasts/{id}` | Update forecast |
| DELETE | `/api/forecasts/{id}` | Delete forecast |

#### Forecast JSON Structure
```json
{
  "id": 1,
  "resourceId": 1,
  "weekEndingDate": "2026-06-14",
  "forecastedHours": 40.00,
  "notes": "Full week on Project Alpha",
  "createdAt": "2026-06-14T10:30:00",
  "updatedAt": "2026-06-14T10:30:00"
}
```

## Sample API Requests

### Create a Resource
```bash
curl -X POST http://localhost:8080/api/resources \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "email": "jane.smith@example.com",
    "department": "Engineering"
  }'
```

### Create a Forecast
```bash
curl -X POST http://localhost:8080/api/forecasts \
  -H "Content-Type: application/json" \
  -d '{
    "resourceId": 1,
    "weekEndingDate": "2026-06-21",
    "forecastedHours": 40.00,
    "notes": "Working on new feature"
  }'
```

### Get All Resources
```bash
curl http://localhost:8080/api/resources
```

### Get Forecasts by Resource
```bash
curl http://localhost:8080/api/forecasts/resource/1
```

## Features

- ✅ RESTful API design
- ✅ Input validation using Bean Validation
- ✅ CORS enabled for React frontend (http://localhost:3000)
- ✅ H2 in-memory database with sample data
- ✅ Proper error handling with meaningful messages
- ✅ Automatic timestamps (created_at, updated_at)
- ✅ Unique constraints (email, resource+week combination)
- ✅ Foreign key relationships with cascade delete

## Database Schema

### Resources Table
- `id` (BIGINT, Primary Key, Auto-increment)
- `name` (VARCHAR, NOT NULL)
- `email` (VARCHAR, NOT NULL, UNIQUE)
- `department` (VARCHAR, NOT NULL)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

### Forecasts Table
- `id` (BIGINT, Primary Key, Auto-increment)
- `resource_id` (BIGINT, Foreign Key → resources.id)
- `week_ending_date` (DATE, NOT NULL)
- `forecasted_hours` (DECIMAL(5,2), NOT NULL)
- `notes` (TEXT)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)
- Unique constraint on (resource_id, week_ending_date)

## Configuration

The application can be configured via `src/main/resources/application.properties`:

- Server port: `server.port=8080`
- Database URL: `spring.datasource.url=jdbc:h2:mem:forecastdb`
- H2 Console: `spring.h2.console.enabled=true`

## Error Handling

The API returns appropriate HTTP status codes:

- `200 OK` - Successful GET, PUT, DELETE
- `201 Created` - Successful POST
- `400 Bad Request` - Validation errors or business logic violations
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Unexpected errors

Error responses include a descriptive message:
```json
{
  "error": "Resource not found with id: 123"
}
```

## Development Notes

- The application uses Lombok to reduce boilerplate code
- All entities have automatic timestamp management
- CORS is configured to allow requests from http://localhost:3000
- Sample data is automatically loaded on startup via schema.sql

## License

This project is part of the Resource Labor Forecast Hours system.