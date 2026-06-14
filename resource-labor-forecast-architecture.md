
# Resource Labor Forecast Hours - System Architecture Document

## 1. System Overview

The Resource Labor Forecast Hours application is a full-stack web application that enables organizations to track and manage resource labor forecasts on a weekly basis. The system supports role-based access control, CRUD operations for forecast entries, and reporting capabilities.

### 1.1 High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         Client Layer                             │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │           React SPA (Single Page Application)             │  │
│  │  - Authentication UI    - Forecast Management UI          │  │
│  │  - Dashboard            - Reporting UI                    │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ HTTPS/REST API
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      Application Layer                           │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │              Spring Boot Application                      │  │
│  │  ┌────────────┐  ┌────────────┐  ┌────────────┐         │  │
│  │  │   Auth     │  │  Forecast  │  │  Reporting │         │  │
│  │  │  Service   │  │  Service   │  │  Service   │         │  │
│  │  └────────────┘  └────────────┘  └────────────┘         │  │
│  │                                                            │  │
│  │  ┌────────────────────────────────────────────────────┐  │  │
│  │  │         Security Layer (JWT Authentication)         │  │  │
│  │  └────────────────────────────────────────────────────┘  │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ JDBC
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                       Data Layer                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │              PostgreSQL Database                          │  │
│  │  - Users Table      - Forecast Entries Table              │  │
│  │  - Resources Table  - Audit Logs Table                    │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

### 1.2 Technology Stack Summary

| Layer | Technology | Version |
|-------|-----------|---------|
| Frontend | React | 18.x |
| Frontend State | Redux Toolkit | 2.x |
| Frontend Routing | React Router | 6.x |
| Frontend UI | Material-UI (MUI) | 5.x |
| Backend Framework | Spring Boot | 3.2.x |
| Backend Language | Java | 17+ |
| Security | Spring Security + JWT | 6.x |
| Database | PostgreSQL | 15.x |
| ORM | Spring Data JPA | 3.2.x |
| API Documentation | Swagger/OpenAPI | 3.0 |
| Build Tool (Backend) | Maven | 3.9.x |
| Build Tool (Frontend) | Vite | 5.x |
| Containerization | Docker | 24.x |
| Container Orchestration | Docker Compose | 2.x |

---

## 2. Database Schema Design

### 2.1 Entity Relationship Diagram

```
┌─────────────────────┐
│       users         │
├─────────────────────┤
│ id (PK)             │
│ username            │
│ email               │
│ password_hash       │
│ first_name          │
│ last_name           │
│ role                │
│ is_active           │
│ created_at          │
│ updated_at          │
└─────────────────────┘
          │
          │ 1:N
          ▼
┌─────────────────────┐
│     resources       │
├─────────────────────┤
│ id (PK)             │
│ user_id (FK)        │
│ resource_name       │
│ department          │
│ job_title           │
│ is_active           │
│ created_at          │
│ updated_at          │
└─────────────────────┘
          │
          │ 1:N
          ▼
┌─────────────────────┐
│ forecast_entries    │
├─────────────────────┤
│ id (PK)             │
│ resource_id (FK)    │
│ week_ending_date    │
│ forecasted_hours    │
│ notes               │
│ created_by (FK)     │
│ created_at          │
│ updated_at          │
└─────────────────────┘
          │
          │ 1:N
          ▼
┌─────────────────────┐
│    audit_logs       │
├─────────────────────┤
│ id (PK)             │
│ entity_type         │
│ entity_id           │
│ action              │
│ user_id (FK)        │
│ old_value           │
│ new_value           │
│ timestamp           │
└─────────────────────┘
```

### 2.2 Table Definitions

#### 2.2.1 users Table
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'USER')),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
```

#### 2.2.2 resources Table
```sql
CREATE TABLE resources (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    resource_name VARCHAR(100) NOT NULL,
    department VARCHAR(100),
    job_title VARCHAR(100),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, resource_name)
);

CREATE INDEX idx_resources_user_id ON resources(user_id);
CREATE INDEX idx_resources_department ON resources(department);
CREATE INDEX idx_resources_is_active ON resources(is_active);
```

#### 2.2.3 forecast_entries Table
```sql
CREATE TABLE forecast_entries (
    id BIGSERIAL PRIMARY KEY,
    resource_id BIGINT NOT NULL REFERENCES resources(id) ON DELETE CASCADE,
    week_ending_date DATE NOT NULL,
    forecasted_hours DECIMAL(5,2) NOT NULL CHECK (forecasted_hours >= 0 AND forecasted_hours <= 168),
    notes TEXT,
    created_by BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(resource_id, week_ending_date)
);

CREATE INDEX idx_forecast_resource_id ON forecast_entries(resource_id);
CREATE INDEX idx_forecast_week_ending ON forecast_entries(week_ending_date);
CREATE INDEX idx_forecast_created_by ON forecast_entries(created_by);
```

#### 2.2.4 audit_logs Table
```sql
CREATE TABLE audit_logs (
    id BIGSERIAL PRIMARY KEY,
    entity_type VARCHAR(50) NOT NULL,
    entity_id BIGINT NOT NULL,
    action VARCHAR(20) NOT NULL CHECK (action IN ('CREATE', 'UPDATE', 'DELETE')),
    user_id BIGINT REFERENCES users(id),
    old_value JSONB,
    new_value JSONB,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_audit_entity ON audit_logs(entity_type, entity_id);
CREATE INDEX idx_audit_user ON audit_logs(user_id);
CREATE INDEX idx_audit_timestamp ON audit_logs(timestamp);
```

---

## 3. REST API Specifications

### 3.1 API Base URL
```
Production: https://api.resourceforecast.com/api/v1
Development: http://localhost:8080/api/v1
```

### 3.2 Authentication Endpoints

#### 3.2.1 POST /auth/register
Register a new user account.

**Request:**
```json
{
  "username": "john.doe",
  "email": "john.doe@example.com",
  "password": "SecurePass123!",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "username": "john.doe",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "USER",
  "isActive": true,
  "createdAt": "2026-06-14T05:30:00Z"
}
```

#### 3.2.2 POST /auth/login
Authenticate user and receive JWT token.

**Request:**
```json
{
  "username": "john.doe",
  "password": "SecurePass123!"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiresIn": 86400,
  "user": {
    "id": 1,
    "username": "john.doe",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "USER"
  }
}
```

#### 3.2.3 POST /auth/refresh
Refresh JWT token.

**Headers:**
```
Authorization: Bearer <token>
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiresIn": 86400
}
```

#### 3.2.4 POST /auth/logout
Logout user (invalidate token).

**Headers:**
```
Authorization: Bearer <token>
```

**Response (200 OK):**
```json
{
  "message": "Logged out successfully"
}
```

### 3.3 User Management Endpoints

#### 3.3.1 GET /users
Get all users (Admin only).

**Headers:**
```
Authorization: Bearer <token>
```

**Query Parameters:**
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 20)
- `role` (optional): Filter by role (ADMIN, USER)
- `isActive` (optional): Filter by active status (true, false)

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "username": "john.doe",
      "email": "john.doe@example.com",
      "firstName": "John",
      "lastName": "Doe",
      "role": "USER",
      "isActive": true,
      "createdAt": "2026-06-14T05:30:00Z"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 1,
  "totalPages": 1
}
```

#### 3.3.2 GET /users/{id}
Get user by ID.

**Headers:**
```
Authorization: Bearer <token>
```

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "john.doe",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "USER",
  "isActive": true,
  "createdAt": "2026-06-14T05:30:00Z",
  "updatedAt": "2026-06-14T05:30:00Z"
}
```

#### 3.3.3 PUT /users/{id}
Update user (Admin or self).

**Headers:**
```
Authorization: Bearer <token>
```

**Request:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "john.doe",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "USER",
  "isActive": true,
  "updatedAt": "2026-06-14T06:00:00Z"
}
```

#### 3.3.4 DELETE /users/{id}
Deactivate user (Admin only).

**Headers:**
```
Authorization: Bearer <token>
```

**Response (204 No Content)**

### 3.4 Resource Management Endpoints

#### 3.4.1 GET /resources
Get all resources.

**Headers:**
```
Authorization: Bearer <token>
```

**Query Parameters:**
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 20)
- `department` (optional): Filter by department
- `isActive` (optional): Filter by active status (true, false)
- `userId` (optional): Filter by user ID (Admin only)

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "userId": 1,
      "resourceName": "John Doe - Developer",
      "department": "Engineering",
      "jobTitle": "Senior Developer",
      "isActive": true,
      "createdAt": "2026-06-14T05:30:00Z"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 1,
  "totalPages": 1
}
```

#### 3.4.2 GET /resources/{id}
Get resource by ID.

**Headers:**
```
Authorization: Bearer <token>
```

**Response (200 OK):**
```json
{
  "id": 1,
  "userId": 1,
  "resourceName": "John Doe - Developer",
  "department": "Engineering",
  "jobTitle": "Senior Developer",
  "isActive": true,
  "createdAt": "2026-06-14T05:30:00Z",
  "updatedAt": "2026-06-14T05:30:00Z"
}
```

#### 3.4.3 POST /resources
Create new resource.

**Headers:**
```
Authorization: Bearer <token>
```

**Request:**
```json
{
  "resourceName": "John Doe - Developer",
  "department": "Engineering",
  "jobTitle": "Senior Developer"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "userId": 1,
  "resourceName": "John Doe - Developer",
  "department": "Engineering",
  "jobTitle": "Senior Developer",
  "isActive": true,
  "createdAt": "2026-06-14T05:30:00Z"
}
```

#### 3.4.4 PUT /resources/{id}
Update resource.

**Headers:**
```
Authorization: Bearer <token>
```

**Request:**
```json
{
  "resourceName": "John Doe - Senior Developer",
  "department": "Engineering",
  "jobTitle": "Lead Developer"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "userId": 1,
  "resourceName": "John Doe - Senior Developer",
  "department": "Engineering",
  "jobTitle": "Lead Developer",
  "isActive": true,
  "updatedAt": "2026-06-14T06:00:00Z"
}
```

#### 3.4.5 DELETE /resources/{id}
Delete resource.

**Headers:**
```
Authorization: Bearer <token>
```

**Response (204 No Content)**

### 3.5 Forecast Entry Endpoints

#### 3.5.1 GET /forecasts
Get all forecast entries.

**Headers:**
```
Authorization: Bearer <token>
```

**Query Parameters:**
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 20)
- `resourceId` (optional): Filter by resource ID
- `startDate` (optional): Filter by start date (ISO 8601 format)
- `endDate` (optional): Filter by end date (ISO 8601 format)
- `department` (optional): Filter by department

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "resourceId": 1,
      "resourceName": "John Doe - Developer",
      "department": "Engineering",
      "weekEndingDate": "2026-06-20",
      "forecastedHours": 40.00,
      "notes": "Regular work week",
      "createdBy": 1,
      "createdByName": "John Doe",
      "createdAt": "2026-06-14T05:30:00Z",
      "updatedAt": "2026-06-14T05:30:00Z"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 1,
  "totalPages": 1
}
```

#### 3.5.2 GET /forecasts/{id}
Get forecast entry by ID.

**Headers:**
```
Authorization: Bearer <token>
```

**Response (200 OK):**
```json
{
  "id": 1,
  "resourceId": 1,
  "resourceName": "John Doe - Developer",
  "department": "Engineering",
  "weekEndingDate": "2026-06-20",
  "forecastedHours": 40.00,
  "notes": "Regular work week",
  "createdBy": 1,
  "createdByName": "John Doe",
  "createdAt": "2026-06-14T05:30:00Z",
  "updatedAt": "2026-06-14T05:30:00Z"
}
```

#### 3.5.3 POST /forecasts
Create new forecast entry.

**Headers:**
```
Authorization: Bearer <token>
```

**Request:**
```json
{
  "resourceId": 1,
  "weekEndingDate": "2026-06-20",
  "forecastedHours": 40.00,
  "notes": "Regular work week"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "resourceId": 1,
  "resourceName": "John Doe - Developer",
  "department": "Engineering",
  "weekEndingDate": "2026-06-20",
  "forecastedHours": 40.00,
  "notes": "Regular work week",
  "createdBy": 1,
  "createdByName": "John Doe",
  "createdAt": "2026-06-14T05:30:00Z"
}
```

#### 3.5.4 PUT /forecasts/{id}
Update forecast entry.

**Headers:**
```
Authorization: Bearer <token>
```

**Request:**
```json
{
  "forecastedHours": 45.00,
  "notes": "Overtime expected"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "resourceId": 1,
  "resourceName": "John Doe - Developer",
  "department": "Engineering",
  "weekEndingDate": "2026-06-20",
  "forecastedHours": 45.00,
  "notes": "Overtime expected",
  "createdBy": 1,
  "createdByName": "John Doe",
  "updatedAt": "2026-06-14T06:00:00Z"
}
```

#### 3.5.5 DELETE /forecasts/{id}
Delete forecast entry.

**Headers:**
```
Authorization: Bearer <token>
```

**Response (204 No Content)**

#### 3.5.6 POST /forecasts/bulk
Create multiple forecast entries at once.

**Headers:**
```
Authorization: Bearer <token>
```

**Request:**
```json
{
  "entries": [
    {
      "resourceId": 1,
      "weekEndingDate": "2026-06-20",
      "forecastedHours": 40.00,
      "notes": "Week 1"
    },
    {
      "resourceId": 1,
      "weekEndingDate": "2026-06-27",
      "forecastedHours": 40.00,
      "notes": "Week 2"
    }
  ]
}
```

**Response (201 Created):**
```json
{
  "created": 2,
  "entries": [
    {
      "id": 1,
      "resourceId": 1,
      "weekEndingDate": "2026-06-20",
      "forecastedHours": 40.00
    },
    {
      "id": 2,
      "resourceId": 1,
      "weekEndingDate": "2026-06-27",
      "forecastedHours": 40.00
    }
  ]
}
```

### 3.6 Reporting Endpoints

#### 3.6.1 GET /reports/weekly-summary
Get weekly summary report.

**Headers:**
```
Authorization: Bearer <token>
```

**Query Parameters:**
- `startDate` (required): Start date (ISO 8601 format)
- `endDate` (required): End date (ISO 8601 format)
- `department` (optional): Filter by department
- `resourceId` (optional): Filter by resource ID

**Response (200 OK):**
```json
{
  "startDate": "2026-06-01",
  "endDate": "2026-06-30",
  "totalHours": 160.00,
  "averageHoursPerWeek": 40.00,
  "resourceCount": 1,
  "weeklyBreakdown": [
    {
      "weekEndingDate": "2026-06-07",
      "totalHours": 40.00,
      "resourceCount": 1
    },
    {
      "weekEndingDate": "2026-06-14",
      "totalHours": 40.00,
      "resourceCount": 1
    }
  ],
  "departmentBreakdown": [
    {
      "department": "Engineering",
      "totalHours": 160.00,
      "resourceCount": 1
    }
  ]
}
```

#### 3.6.2 GET /reports/resource-utilization
Get resource utilization report.

**Headers:**
```
Authorization: Bearer <token>
```

**Query Parameters:**
- `startDate` (required): Start date (ISO 8601 format)
- `endDate` (required): End date (ISO 8601 format)
- `department` (optional): Filter by department

**Response (200 OK):**
```json
{
  "startDate": "2026-06-01",
  "endDate": "2026-06-30",
  "resources": [
    {
      "resourceId": 1,
      "resourceName": "John Doe - Developer",
      "department": "Engineering",
      "totalHours": 160.00,
      "averageHoursPerWeek": 40.00,
      "utilizationPercentage": 100.00,
      "weekCount": 4
    }
  ]
}
```

#### 3.6.3 GET /reports/export/excel
Export report to Excel format.

**Headers:**
```
Authorization: Bearer <token>
```

**Query Parameters:**
- `reportType` (required): Type of report (weekly-summary, resource-utilization)
- `startDate` (required): Start date (ISO 8601 format)
- `endDate` (required): End date (ISO 8601 format)
- `department` (optional): Filter by department

**Response (200 OK):**
- Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
- Binary Excel file download

#### 3.6.4 GET /reports/export/csv
Export report to CSV format.

**Headers:**
```
Authorization: Bearer <token>
```

**Query Parameters:**
- `reportType` (required): Type of report (weekly-summary, resource-utilization)
- `startDate` (required): Start date (ISO 8601 format)
- `endDate` (required): End date (ISO 8601 format)
- `department` (optional): Filter by department

**Response (200 OK):**
- Content-Type: text/csv
- CSV file download

### 3.7 Error Response Format

All error responses follow this format:

```json
{
  "timestamp": "2026-06-14T05:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed for field 'forecastedHours': must be between 0 and 168",
  "path": "/api/v1/forecasts"
}
```

**Common HTTP Status Codes:**
- 200: OK
- 201: Created
- 204: No Content
- 400: Bad Request (validation errors)
- 401: Unauthorized (missing or invalid token)
- 403: Forbidden (insufficient permissions)
- 404: Not Found
- 409: Conflict (duplicate entry)
- 500: Internal Server Error

---

## 4. Frontend Architecture

### 4.1 Component Hierarchy

```
App
├── AuthProvider (Context)
├── ThemeProvider (Context)
├── Router
    ├── PublicLayout
    │   ├── LoginPage
    │   └── RegisterPage
    └── PrivateLayout
        ├── Header
        │   ├── Logo
        │   ├── Navigation
        │   └── UserMenu
        ├── Sidebar (optional)
        └── Routes
            ├── DashboardPage
            │   ├── WeeklySummaryWidget
            │   ├── ResourceUtilizationWidget
            │   └── RecentForecastsWidget
            ├── ForecastsPage
            │   ├── ForecastFilters
            │   ├── ForecastTable
            │   │   ├── ForecastRow
            │   │   └── ForecastActions
            │   ├── ForecastForm (Modal/Drawer)
            │   └── BulkEntryForm (Modal/Drawer)
            ├── ResourcesPage
            │   ├── ResourceFilters
            │   ├── ResourceTable
            │   │   ├── ResourceRow
            │   │   └── ResourceActions
            │   └── ResourceForm (Modal/Drawer)
            ├── ReportsPage
            │   ├── ReportFilters
            │   ├── WeeklySummaryReport
            │   ├── ResourceUtilizationReport
            │   └── ExportButtons
            └── UsersPage (Admin only)
                ├── UserFilters
                ├── UserTable
                │   ├── UserRow
                │   └── UserActions
                └── UserForm (Modal/Drawer)
```

### 4.2 Routing Structure

```javascript
// Route definitions
const routes = [
  // Public routes
  { path: '/login', component: LoginPage, public: true },
  { path: '/register', component: RegisterPage, public: true },
  
  // Private routes
  { path: '/', component: DashboardPage, protected: true },
  { path: '/forecasts', component: ForecastsPage, protected: true },
  { path: '/resources', component: ResourcesPage, protected: true },
  { path: '/reports', component: ReportsPage, protected: true },
  
  // Admin only routes
  { path: '/users', component: UsersPage, protected: true, roles: ['ADMIN'] },
  
  // Fallback
  { path: '*', component: NotFoundPage }
];
```

### 4.3 State Management Structure

```javascript
// Redux store structure
{
  auth: {
    user: null | User,
    token: null | string,
    isAuthenticated: boolean,
    loading: boolean,
    error: null | string
  },
  forecasts: {
    items: Forecast[],
    selectedForecast: null | Forecast,
    filters: {
      resourceId: null | number,
      startDate: null | string,
      endDate: null | string,
      department: null | string
    },
    pagination: {
      page: number,
      size: number,
      totalElements: number,
      totalPages: number
    },
    loading: boolean,
    error: null | string
  },
  resources: {
    items: Resource[],
    selectedResource: null | Resource,
    filters: {
      department: null | string,
      isActive: boolean
    },
    pagination: {
      page: number,
      size: number,
      totalElements: number,
      totalPages: number
    },
    loading: boolean,
    error: null | string
  },
  reports: {
    weeklySummary: null | WeeklySummaryReport,
    resourceUtilization: null | ResourceUtilizationReport,
    loading: boolean,
    error: null | string
  },
  users: {
    items: User[],
    selectedUser: null | User,
    filters: {
      role: null | string,
      isActive: boolean
    },
    pagination: {
      page: number,
      size: number,
      totalElements: number,
      totalPages: number
    },
    loading: boolean,
    error: null | string
  },
  ui: {
    sidebarOpen: boolean,
    theme: 'light' | 'dark',
    notifications: Notification[]
  }
}
```

### 4.4 Key React Components

#### 4.4.1 ForecastTable Component
```javascript
// ForecastTable.jsx
import React from 'react';
import { DataGrid } from '@mui/x-data-grid';

const ForecastTable = ({ forecasts, onEdit, onDelete, loading }) => {
  const columns = [
    { field: 'id', headerName: 'ID', width: 70 },
    { field: 'resourceName', headerName: 'Resource', width: 200 },
    { field: 'department', headerName: 'Department', width: 150 },
    { field: 'weekEndingDate', headerName: 'Week Ending', width: 130 },
    { field: 'forecastedHours', headerName: 'Hours', width: 100 },
    { field: 'notes', headerName: 'Notes', width: 200 },
    {
      field: 'actions',
      headerName: 'Actions',
      width: 150,
      renderCell: (params) => (
        <>
          <IconButton onClick={() => onEdit(params.row)}>
            <EditIcon />
          </IconButton>
          <IconButton onClick={() => onDelete(params.row.id)}>
            <DeleteIcon />
          </IconButton>
        </>
      )
    }
  ];

  return (
    <DataGrid
      rows={forecasts}
      columns={columns}
      loading={loading}
      pageSize={20}
      rowsPerPageOptions={[10, 20, 50]}
      checkboxSelection
      disableSelectionOnClick
    />
  );
};
```

#### 4.4.2 ForecastForm Component
```javascript
// ForecastForm.jsx
import React from 'react';
import { useForm, Controller } from 'react-hook-form';
import { TextField, Button, Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';

const ForecastForm = ({ open, onClose, onSubmit, initialData, resources }) => {
  const { control, handleSubmit, formState: { errors } } = useForm({
    defaultValues: initialData || {
      resourceId: '',
      weekEndingDate: '',
      forecastedHours: '',
      notes: ''
    }
  });

  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>
        {initialData ? 'Edit Forecast' : 'Add Forecast'}
      </DialogTitle>
      <DialogContent>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Controller
            name="resourceId"
            control={control}
            rules={{ required: 'Resource is required' }}
            render={({ field }) => (
              <TextField
                {...field}
                select
                label="Resource"
                fullWidth
                margin="normal"
                error={!!errors.resourceId}
                helperText={errors.resourceId?.message}
              >
                {resources.map(resource => (
                  <MenuItem key={resource.id} value={resource.id}>
                    {resource.resourceName}
                  </MenuItem>
                ))}
              </TextField>
            )}
          />
          <Controller
            name="weekEndingDate"
            control={control}
            rules={{ required: 'Week ending date is required' }}
            render={({ field }) => (
              <TextField
                {...field}
                type="date"
                label="Week Ending Date"
                fullWidth
                margin="normal"
                InputLabelProps={{ shrink: true }}
                error={!!errors.weekEndingDate}
                helperText={errors.weekEndingDate?.message}
              />
            )}
          />
          <Controller
            name="forecastedHours"
            control={control}
            rules={{
              required: 'Hours is required',
              min: { value: 0, message: 'Hours must be at least 0' },
              max: { value: 168, message: 'Hours cannot exceed 168' }
            }}
            render={({ field }) => (
              <TextField
                {...field}
                type="number"
                label="Forecasted Hours"
                fullWidth
                margin="normal"
                error={!!errors.forecastedHours}
                helperText={errors.forecastedHours?.message}
              />
            )}
          />
          <Controller
            name="notes"
            control={control}
            render={({ field }) => (
              <TextField
                {...field}
                label="Notes"
                fullWidth
                margin="normal"
                multiline
                rows={3}
              />
            )}
          />
        </form>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Cancel</Button>
        <Button onClick={handleSubmit(onSubmit)} variant="contained" color="primary">
          {initialData ? 'Update' : 'Create'}
        </Button>
      </DialogActions>
    </Dialog>
  );
};
```

---

## 5. Color Scheme and UI Design

### 5.1 Color Palette

```javascript
// theme.js - Material-UI theme configuration
const theme = createTheme({
  palette: {
    primary: {
      main: '#1976D2',      // Primary Blue
      light: '#42A5F5',     // Light Blue
      dark: '#1565C0',      // Dark Blue
      contrastText: '#FFFFFF'
    },
    secondary: {
      main: '#424242',      // Dark Gray (almost black)
      light: '#616161',     // Medium Gray
      dark: '#212121',      // Very Dark Gray
      contrastText: '#FFFFFF'
    },
    background: {
      default: '#FAFAFA',   // Light Gray Background
      paper: '#FFFFFF'      // White Paper
    },
    text: {
      primary: '#212121',   // Primary Text (Dark)
      secondary: '#757575'  // Secondary Text (Gray)
    },
    error: {
      main: '#D32F2F'
    },
    warning: {
      main: '#F57C00'
    },
    success: {
      main: '#388E3C'
    },
    info: {
      main: '#1976D2'
    }
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
    h1: {
      fontSize: '2.5rem',
      fontWeight: 500,
      color: '#212121'
    },
    h2: {
      fontSize: '2rem',
      fontWeight: 500,
      color: '#212121'
    },
    h3: {
      fontSize: '1.75rem',
      fontWeight: 500,
      color: '#212121'
    },
    h4: {
      fontSize: '1.5rem',
      fontWeight: 500,
      color: '#212121'
    },
    h5: {
      fontSize: '1.25rem',
      fontWeight: 500,
      color: '#212121'
    },
    h6: {
      fontSize: '1rem',
      fontWeight: 500,
      color: '#212121'
    }
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
          borderRadius: 4
        },
        contained: {
          boxShadow: 'none',
          '&:hover': {
            boxShadow: '0px 2px 4px rgba(0, 0, 0, 0.2)'
          }
        }
      }
    },
    MuiCard: {
      styleOverrides: {
        root: {
          borderRadius: 8,
          boxShadow: '0px 2px 8px rgba(0, 0, 0, 0.1)'
        }
      }
    },
    MuiAppBar: {
      styleOverrides: {
        root: {
          backgroundColor: '#1976D2',
          color: '#FFFFFF'
        }
      }
    }
  }
});
```

### 5.2 UI Component Styling Guidelines

**Header/Navigation:**
- Background: Primary Blue (#1976D2)
- Text: White (#FFFFFF)
- Active link: Light Blue (#42A5F5)

**Sidebar (if used):**
- Background: White (#FFFFFF)
- Border: Light Gray (#E0E0E0)
- Hover: Light Blue (#E3F2FD)

**Tables:**
- Header: Light Blue (#E3F2FD)
- Rows: Alternating White (#FFFFFF) and Very Light Gray (#FAFAFA)
- Hover: Light Blue (#E3F2FD)
- Border: Light Gray (#E0E0E0)

**Forms:**
- Input borders: Medium Gray (#BDBDBD)
- Focus: Primary Blue (#1976D2)
- Error: Red (#D32F2F)
- Labels: Dark Gray (#424242)

**Buttons:**
- Primary: Blue (#1976D2) with White text
- Secondary: Dark Gray (#424242) with White text
- Outlined: Blue border (#1976D2) with Blue text
- Disabled: Light Gray (#E0E0E0)

**Cards/Widgets:**
- Background: White (#FFFFFF)
- Border: Light Gray (#E0E0E0)
- Shadow: Subtle gray shadow

---

## 6. Backend Architecture Details

### 6.1 Project Structure

```
resource-forecast-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── resourceforecast/
│   │   │           ├── ResourceForecastApplication.java
│   │   │           ├── config/
│   │   │           │   ├── SecurityConfig.java
│   │   │           │   ├── JwtConfig.java
│   │   │           │   ├── SwaggerConfig.java
│   │   │           │   └── CorsConfig.java
│   │   │           ├── controller/
│   │   │           │   ├── AuthController.java
│   │   │           │   ├── UserController.java
│   │   │           │   ├── ResourceController.java
│   │   │           │   ├── ForecastController.java
│   │   │           │   └── ReportController.java
│   │   │           ├── service/
│   │   │           │   ├── AuthService.java
│   │   │           │   ├── UserService.java
│   │   │           │   ├── ResourceService.java
│   │   │           │   ├── ForecastService.java
│   │   │           │   ├── ReportService.java
│   │   │           │   └── AuditService.java
│   │   │           ├── repository/
│   │   │           │   ├── UserRepository.java
│   │   │           │   ├── ResourceRepository.java
│   │   │           │   ├── ForecastEntryRepository.java
│   │   │           │   └── AuditLogRepository.java
│   │   │           ├── model/
│   │   │           │   ├── entity/
│   │   │           │   │   ├── User.java
│   │   │           │   │   ├── Resource.java
│   │   │           │   │   ├── ForecastEntry.java
│   │   │           │   │   └── AuditLog.java
│   │   │           │   ├── dto/
│   │   │           │   │   ├── request/
│   │   │           │   │   │   ├── LoginRequest.java
│   │   │           │   │   │   ├── RegisterRequest.java
│   │   │           │   │   │   ├── ForecastRequest.java
│   │   │           │   │   │   └── ResourceRequest.java
│   │   │           │   │   └── response/
│   │   │           │   │       ├── AuthResponse.java
│   │   │           │   │       ├── ForecastResponse.java
│   │   │           │   │       ├── ResourceResponse.java
│   │   │           │   │       └── ReportResponse.java
│   │   │           │   └── enums/
│   │   │           │       ├── Role.java
│   │   │           │       └── AuditAction.java
│   │   │           ├── security/
│   │   │           │   ├── JwtTokenProvider.java
│   │   │           │   ├── JwtAuthenticationFilter.java
│   │   │           │   └── UserDetailsServiceImpl.java
│   │   │           ├── exception/
│   │   │           │   ├── GlobalExceptionHandler.java
│   │   │           │   ├── ResourceNotFoundException.java
│   │   │           │   ├── DuplicateResourceException.java
│   │   │           │   └── UnauthorizedException.java
│   │   │           └── util/
│   │   │               ├── DateUtil.java
│   │   │               └── ExcelExportUtil.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-prod.yml
│   │       └── db/
│   │           └── migration/
│   │               ├── V1__create_users_table.sql
│   │               ├── V2__create_resources_table.sql
│   │               ├── V3__create_forecast_entries_table.sql
│   │               └── V4__create_audit_logs_table.sql
│   └── test/
│       └── java/
│           └── com/
│               └── resourceforecast/
│                   ├── controller/
│                   ├── service/
│                   └── repository/
├── pom.xml
├── Dockerfile
└── docker-compose.yml
```

### 6.2 Key Java Classes

#### 6.2.1 ForecastEntry Entity
```java
@Entity
@Table(name = "forecast_entries",
       uniqueConstraints = @UniqueConstraint(columnNames = {"resource_id", "week_ending_date"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForecastEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;
    
    @Column(name = "week_ending_date", nullable = false)
    private LocalDate weekEndingDate;
    
    @Column(name = "forecasted_hours", nullable = false, precision = 5, scale = 2)
    @Min(0)
    @Max(168)
    private BigDecimal forecastedHours;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
```

#### 6.2.2 ForecastService
```java
@Service
@Transactional
public class ForecastService {
    
    @Autowired
    private ForecastEntryRepository forecastRepository;
    
    @Autowired
    private ResourceRepository resourceRepository;
    
