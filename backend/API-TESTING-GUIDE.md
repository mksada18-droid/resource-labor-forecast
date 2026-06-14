# API Testing Guide - Resource Labor Forecast

This guide provides comprehensive examples for testing all API endpoints.

## Prerequisites
- Backend application running on http://localhost:8080
- curl installed (or use Postman/Insomnia)

## Quick Start - Verify Application is Running

```bash
curl http://localhost:8080/api/resources
```

Expected: JSON array with sample resources

---

## Resources API Testing

### 1. Get All Resources
```bash
curl -X GET http://localhost:8080/api/resources
```

**Expected Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "department": "Engineering",
    "createdAt": "2026-06-14T22:45:00",
    "updatedAt": "2026-06-14T22:45:00"
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane.smith@example.com",
    "department": "Engineering",
    "createdAt": "2026-06-14T22:45:00",
    "updatedAt": "2026-06-14T22:45:00"
  }
]
```

### 2. Get Resource by ID
```bash
curl -X GET http://localhost:8080/api/resources/1
```

**Expected Response (200 OK):**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "department": "Engineering",
  "createdAt": "2026-06-14T22:45:00",
  "updatedAt": "2026-06-14T22:45:00"
}
```

**Error Case - Resource Not Found (404):**
```bash
curl -X GET http://localhost:8080/api/resources/999
```

### 3. Create New Resource
```bash
curl -X POST http://localhost:8080/api/resources \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sarah Connor",
    "email": "sarah.connor@example.com",
    "department": "Security"
  }'
```

**Expected Response (201 Created):**
```json
{
  "id": 5,
  "name": "Sarah Connor",
  "email": "sarah.connor@example.com",
  "department": "Security",
  "createdAt": "2026-06-14T22:50:00",
  "updatedAt": "2026-06-14T22:50:00"
}
```

**Validation Error Cases:**

Missing required field:
```bash
curl -X POST http://localhost:8080/api/resources \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User"
  }'
```
Expected: 400 Bad Request with validation errors

Duplicate email:
```bash
curl -X POST http://localhost:8080/api/resources \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Another User",
    "email": "john.doe@example.com",
    "department": "Engineering"
  }'
```
Expected: 400 Bad Request - email already exists

### 4. Update Resource
```bash
curl -X PUT http://localhost:8080/api/resources/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe Updated",
    "email": "john.doe@example.com",
    "department": "Senior Engineering"
  }'
```

**Expected Response (200 OK):**
```json
{
  "id": 1,
  "name": "John Doe Updated",
  "email": "john.doe@example.com",
  "department": "Senior Engineering",
  "createdAt": "2026-06-14T22:45:00",
  "updatedAt": "2026-06-14T22:55:00"
}
```

### 5. Delete Resource
```bash
curl -X DELETE http://localhost:8080/api/resources/5
```

**Expected Response (200 OK):**
```json
{
  "message": "Resource deleted successfully"
}
```

**Note:** Deleting a resource will cascade delete all associated forecasts.

---

## Forecasts API Testing

### 1. Get All Forecasts
```bash
curl -X GET http://localhost:8080/api/forecasts
```

**Expected Response (200 OK):**
```json
[
  {
    "id": 1,
    "resourceId": 1,
    "weekEndingDate": "2026-06-14",
    "forecastedHours": 40.00,
    "notes": "Full week on Project Alpha",
    "createdAt": "2026-06-14T22:45:00",
    "updatedAt": "2026-06-14T22:45:00"
  },
  {
    "id": 2,
    "resourceId": 1,
    "weekEndingDate": "2026-06-21",
    "forecastedHours": 35.00,
    "notes": "Project Alpha and code review",
    "createdAt": "2026-06-14T22:45:00",
    "updatedAt": "2026-06-14T22:45:00"
  }
]
```

### 2. Get Forecast by ID
```bash
curl -X GET http://localhost:8080/api/forecasts/1
```

**Expected Response (200 OK):**
```json
{
  "id": 1,
  "resourceId": 1,
  "weekEndingDate": "2026-06-14",
  "forecastedHours": 40.00,
  "notes": "Full week on Project Alpha",
  "createdAt": "2026-06-14T22:45:00",
  "updatedAt": "2026-06-14T22:45:00"
}
```

### 3. Get Forecasts by Resource ID
```bash
curl -X GET http://localhost:8080/api/forecasts/resource/1
```

**Expected Response (200 OK):**
```json
[
  {
    "id": 1,
    "resourceId": 1,
    "weekEndingDate": "2026-06-14",
    "forecastedHours": 40.00,
    "notes": "Full week on Project Alpha",
    "createdAt": "2026-06-14T22:45:00",
    "updatedAt": "2026-06-14T22:45:00"
  },
  {
    "id": 2,
    "resourceId": 1,
    "weekEndingDate": "2026-06-21",
    "forecastedHours": 35.00,
    "notes": "Project Alpha and code review",
    "createdAt": "2026-06-14T22:45:00",
    "updatedAt": "2026-06-14T22:45:00"
  }
]
```

### 4. Get Forecasts by Week Ending Date
```bash
curl -X GET http://localhost:8080/api/forecasts/week/2026-06-14
```

**Expected Response (200 OK):**
```json
[
  {
    "id": 1,
    "resourceId": 1,
    "weekEndingDate": "2026-06-14",
    "forecastedHours": 40.00,
    "notes": "Full week on Project Alpha",
    "createdAt": "2026-06-14T22:45:00",
    "updatedAt": "2026-06-14T22:45:00"
  },
  {
    "id": 3,
    "resourceId": 2,
    "weekEndingDate": "2026-06-14",
    "forecastedHours": 40.00,
    "notes": "Backend development",
    "createdAt": "2026-06-14T22:45:00",
    "updatedAt": "2026-06-14T22:45:00"
  }
]
```

### 5. Create New Forecast
```bash
curl -X POST http://localhost:8080/api/forecasts \
  -H "Content-Type: application/json" \
  -d '{
    "resourceId": 1,
    "weekEndingDate": "2026-06-28",
    "forecastedHours": 40.00,
    "notes": "Working on new feature implementation"
  }'
```

**Expected Response (201 Created):**
```json
{
  "id": 7,
  "resourceId": 1,
  "weekEndingDate": "2026-06-28",
  "forecastedHours": 40.00,
  "notes": "Working on new feature implementation",
  "createdAt": "2026-06-14T23:00:00",
  "updatedAt": "2026-06-14T23:00:00"
}
```

**Validation Error Cases:**

Invalid resource ID:
```bash
curl -X POST http://localhost:8080/api/forecasts \
  -H "Content-Type: application/json" \
  -d '{
    "resourceId": 999,
    "weekEndingDate": "2026-06-28",
    "forecastedHours": 40.00,
    "notes": "Test"
  }'
```
Expected: 400 Bad Request - Resource not found

Duplicate forecast (same resource + week):
```bash
curl -X POST http://localhost:8080/api/forecasts \
  -H "Content-Type: application/json" \
  -d '{
    "resourceId": 1,
    "weekEndingDate": "2026-06-14",
    "forecastedHours": 40.00,
    "notes": "Duplicate"
  }'
```
Expected: 400 Bad Request - Forecast already exists

Invalid hours (negative or > 168):
```bash
curl -X POST http://localhost:8080/api/forecasts \
  -H "Content-Type: application/json" \
  -d '{
    "resourceId": 1,
    "weekEndingDate": "2026-06-28",
    "forecastedHours": -5.00,
    "notes": "Invalid hours"
  }'
```
Expected: 400 Bad Request - Validation error

### 6. Update Forecast
```bash
curl -X PUT http://localhost:8080/api/forecasts/1 \
  -H "Content-Type: application/json" \
  -d '{
    "resourceId": 1,
    "weekEndingDate": "2026-06-14",
    "forecastedHours": 38.00,
    "notes": "Updated: Reduced hours due to meeting"
  }'
```

**Expected Response (200 OK):**
```json
{
  "id": 1,
  "resourceId": 1,
  "weekEndingDate": "2026-06-14",
  "forecastedHours": 38.00,
  "notes": "Updated: Reduced hours due to meeting",
  "createdAt": "2026-06-14T22:45:00",
  "updatedAt": "2026-06-14T23:05:00"
}
```

### 7. Delete Forecast
```bash
curl -X DELETE http://localhost:8080/api/forecasts/7
```

**Expected Response (200 OK):**
```json
{
  "message": "Forecast deleted successfully"
}
```

---

## Testing Scenarios

### Scenario 1: Complete Resource Lifecycle
```bash
# 1. Create a new resource
curl -X POST http://localhost:8080/api/resources \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Engineer",
    "email": "test.engineer@example.com",
    "department": "QA"
  }'

# Note the returned ID (e.g., 5)

# 2. Get the resource
curl -X GET http://localhost:8080/api/resources/5

# 3. Update the resource
curl -X PUT http://localhost:8080/api/resources/5 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Senior Test Engineer",
    "email": "test.engineer@example.com",
    "department": "QA"
  }'

# 4. Delete the resource
curl -X DELETE http://localhost:8080/api/resources/5
```

### Scenario 2: Forecast Planning for a Resource
```bash
# 1. Get resource ID 1
curl -X GET http://localhost:8080/api/resources/1

# 2. View existing forecasts for this resource
curl -X GET http://localhost:8080/api/forecasts/resource/1

# 3. Add forecast for next week
curl -X POST http://localhost:8080/api/forecasts \
  -H "Content-Type: application/json" \
  -d '{
    "resourceId": 1,
    "weekEndingDate": "2026-07-05",
    "forecastedHours": 40.00,
    "notes": "Sprint planning and development"
  }'

# 4. View all forecasts for the resource again
curl -X GET http://localhost:8080/api/forecasts/resource/1
```

### Scenario 3: Weekly Capacity Planning
```bash
# Get all forecasts for a specific week
curl -X GET http://localhost:8080/api/forecasts/week/2026-06-14

# Calculate total capacity for the week
# (Sum all forecastedHours from the response)
```

---

## Using Postman

### Import Collection
1. Create a new collection in Postman
2. Add requests for each endpoint
3. Set base URL as variable: `{{baseUrl}}` = `http://localhost:8080`

### Sample Postman Request

**GET All Resources:**
- Method: GET
- URL: `{{baseUrl}}/api/resources`
- Headers: None required

**POST Create Resource:**
- Method: POST
- URL: `{{baseUrl}}/api/resources`
- Headers: `Content-Type: application/json`
- Body (raw JSON):
```json
{
  "name": "New Resource",
  "email": "new.resource@example.com",
  "department": "Engineering"
}
```

---

## Troubleshooting

### Issue: Connection Refused
**Solution:** Ensure the backend application is running on port 8080

### Issue: 404 Not Found
**Solution:** Check the URL path. API base is `/api/`

### Issue: 400 Bad Request
**Solution:** Check request body format and required fields

### Issue: Empty Response
**Solution:** Database might be empty. Check H2 console or restart application to reload sample data

---

## Next Steps

1. Test all endpoints systematically
2. Verify data persistence across requests
3. Test error scenarios
4. Build frontend to consume these APIs
5. Add authentication/authorization
6. Implement pagination for large datasets

---
Made with Bob