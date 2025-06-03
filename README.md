# Employee Management System API Documentation

## Overview
REST API for managing employees, access requests, and computer assignments in an organization.

## Authentication

### Login
```bash
curl -X POST "http://localhost:8080/api/auth/login" \
-H "Content-Type: application/json" \
-d '{
    "email": "user@example.com",
    "password": "password123"
}'
```

### Create Password
```bash
curl -X POST "http://localhost:8080/api/auth/create-password" \
-H "Content-Type: application/json" \
-d '{
    "email": "user@example.com",
    "password": "newpassword123"
}'
```

### Validate Token
```bash
curl -X POST "http://localhost:8080/api/auth/validate-token" \
-H "Content-Type: application/json" \
-d '{
    "token": "your-jwt-token"
}'
```

Response:
```json
true
```

The endpoint returns `true` if the token is valid and `false` if it's invalid.

## Access Requests

### Create Access Request
```bash
curl -X POST "http://localhost:8080/api/access-requests" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-jwt-token" \
-d '{
    "employeeId": 1,
    "systemIds": [1, 2, 3],
    "assignedById": 2
}'
```

### Get Access Requests by Employee
```bash
# Without assignedById filter
curl -X GET "http://localhost:8080/api/access-requests/employee/1" \
-H "Authorization: Bearer your-jwt-token"

# With assignedById filter
curl -X GET "http://localhost:8080/api/access-requests/employee/1?assignedById=2" \
-H "Authorization: Bearer your-jwt-token"
```

### Update Access Request Status
```bash
curl -X PUT "http://localhost:8080/api/access-requests/1/status" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-jwt-token" \
-d '{
    "status": "APPROVED"
}'
```

## Computer Assignments

### Create Computer Assignment
```bash
curl -X POST "http://localhost:8080/api/computer-assignments" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-jwt-token" \
-d '{
    "employeeId": 1,
    "computerId": 2,
    "assignedById": 3
}'
```

### Update Computer Assignment Status
```bash
curl -X PUT "http://localhost:8080/api/computer-assignments/1/status" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer your-jwt-token" \
-d '{
    "status": "APPROVED"
}'
```

### Get Computer Assignments by Employee
```bash
curl -X GET "http://localhost:8080/api/computer-assignments/employee/1" \
-H "Authorization: Bearer your-jwt-token"
```

### Get Active Computer Assignments
```bash
curl -X GET "http://localhost:8080/api/computer-assignments/active" \
-H "Authorization: Bearer your-jwt-token"
```

### Get Available Computers
```bash
curl -X GET "http://localhost:8080/api/computer-assignments/available" \
-H "Authorization: Bearer your-jwt-token"
```

## Systems

### Get All Systems
```bash
curl -X GET "http://localhost:8080/api/systems" \
-H "Authorization: Bearer your-jwt-token"
```

Response:
```json
[
  {
    "id": 1,
    "name": "Email System",
    "description": "Corporate email system access"
  },
  {
    "id": 2,
    "name": "CRM",
    "description": "Customer Relationship Management System"
  }
]
```

### Get System by ID
```bash
curl -X GET "http://localhost:8080/api/systems/1" \
-H "Authorization: Bearer your-jwt-token"
```

Response:
```json
{
  "id": 1,
  "name": "Email System",
  "description": "Corporate email system access"
}
```

## Status Values

### Access Requests
- PENDING: Initial state when request is created
- APPROVED: Request has been approved
- REJECTED: Request has been rejected
- CANCELLED: Request has been cancelled

### Computer Assignments
- PENDING: Initial state when assignment is created
- APPROVED: Assignment has been approved
- REJECTED: Assignment has been rejected
- CANCELLED: Assignment has been cancelled

### Computer Status
- AVAILABLE: Computer is available for assignment
- ASSIGNED: Computer is currently assigned to an employee

## Error Handling
All endpoints return appropriate HTTP status codes:
- 200: Success
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error

## Authentication
All endpoints except `/api/auth/*` require a valid JWT token in the Authorization header:
```
Authorization: Bearer your-jwt-token
```