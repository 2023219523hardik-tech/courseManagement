# OCMS API Documentation

## Base URL
```
http://localhost:8080/api/v1
```

## Authentication
All protected endpoints require a JWT token in the Authorization header:
```
Authorization: Bearer <jwt-token>
```

## Endpoints

### Authentication

#### Register User
```http
POST /auth/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "password123",
  "role": "STUDENT",
  "phoneNumber": "+1234567890"
}
```

**Response:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "role": "STUDENT",
  "phoneNumber": "+1234567890",
  "fullName": "John Doe",
  "createdAt": "2024-01-01T10:00:00",
  "isActive": true
}
```

#### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "john.doe@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "role": "STUDENT",
    "fullName": "John Doe",
    "isActive": true
  }
}
```

#### Get Current User
```http
GET /auth/me
Authorization: Bearer <jwt-token>
```

### User Management

#### Get All Users
```http
GET /users
Authorization: Bearer <jwt-token>
```

#### Get User by ID
```http
GET /users/{id}
Authorization: Bearer <jwt-token>
```

#### Get Active Users
```http
GET /users/active
Authorization: Bearer <jwt-token>
```

#### Get Users by Role
```http
GET /users/role/{role}
Authorization: Bearer <jwt-token>
```

#### Get All Instructors
```http
GET /users/instructors
Authorization: Bearer <jwt-token>
```

#### Get All Students
```http
GET /users/students
Authorization: Bearer <jwt-token>
```

#### Search Users by Name
```http
GET /users/search?name=John
Authorization: Bearer <jwt-token>
```

#### Update User
```http
PUT /users/{id}
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Updated",
  "email": "john.updated@example.com",
  "role": "INSTRUCTOR",
  "phoneNumber": "+1234567890"
}
```

#### Deactivate User
```http
DELETE /users/{id}
Authorization: Bearer <jwt-token>
```

#### Activate User
```http
PUT /users/{id}/activate
Authorization: Bearer <jwt-token>
```

### Course Management

#### Create Course
```http
POST /courses
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "title": "Introduction to Spring Boot",
  "description": "Learn the fundamentals of Spring Boot framework",
  "duration": 40,
  "maxStudents": 30
}
```

**Response:**
```json
{
  "id": 1,
  "title": "Introduction to Spring Boot",
  "description": "Learn the fundamentals of Spring Boot framework",
  "duration": 40,
  "maxStudents": 30,
  "enrolledStudentCount": 0,
  "isActive": true,
  "createdAt": "2024-01-01T10:00:00",
  "instructor": {
    "id": 1,
    "firstName": "John",
    "lastName": "Smith",
    "email": "john.smith@ocms.com",
    "role": "INSTRUCTOR",
    "fullName": "John Smith"
  },
  "isFull": false,
  "canEnroll": true
}
```

#### Get All Active Courses
```http
GET /courses
Authorization: Bearer <jwt-token>
```

#### Get Course by ID
```http
GET /courses/{id}
Authorization: Bearer <jwt-token>
```

#### Get My Courses (Instructor)
```http
GET /courses/my-courses
Authorization: Bearer <jwt-token>
```

#### Search Courses by Title
```http
GET /courses/search/title?title=Spring
Authorization: Bearer <jwt-token>
```

#### Search Courses by Description
```http
GET /courses/search/description?description=framework
Authorization: Bearer <jwt-token>
```

#### Get Available Courses
```http
GET /courses/available
Authorization: Bearer <jwt-token>
```

#### Get Courses by Duration Range
```http
GET /courses/duration?minDuration=20&maxDuration=60
Authorization: Bearer <jwt-token>
```

#### Get Popular Courses
```http
GET /courses/popular?minEnrollment=5
Authorization: Bearer <jwt-token>
```

#### Update Course
```http
PUT /courses/{id}
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "title": "Advanced Spring Boot",
  "description": "Advanced concepts in Spring Boot",
  "duration": 60,
  "maxStudents": 25
}
```

#### Deactivate Course
```http
DELETE /courses/{id}
Authorization: Bearer <jwt-token>
```

### Enrollment Management

#### Enroll Student
```http
POST /enrollments/enroll?studentId=1&courseId=1
Authorization: Bearer <jwt-token>
```

**Response:**
```json
"Student Alice Johnson successfully enrolled in Introduction to Spring Boot"
```

#### Drop Course
```http
POST /enrollments/drop?studentId=1&courseId=1
Authorization: Bearer <jwt-token>
```

#### Get Enrollments by Student
```http
GET /enrollments/student/{studentId}
Authorization: Bearer <jwt-token>
```

**Response:**
```json
[
  {
    "id": 1,
    "student": {
      "id": 1,
      "firstName": "Alice",
      "lastName": "Johnson",
      "email": "alice.johnson@student.com",
      "fullName": "Alice Johnson"
    },
    "courseId": 1,
    "courseTitle": "Introduction to Spring Boot",
    "enrolledAt": "2024-01-01T10:00:00",
    "isActive": true,
    "droppedAt": null
  }
]
```

#### Get Enrollments by Course
```http
GET /enrollments/course/{courseId}
Authorization: Bearer <jwt-token>
```

#### Check Enrollment Status
```http
GET /enrollments/check?studentId=1&courseId=1
Authorization: Bearer <jwt-token>
```

**Response:**
```json
true
```

#### Get Enrollment Count
```http
GET /enrollments/count/{courseId}
Authorization: Bearer <jwt-token>
```

#### Get Recent Enrollments
```http
GET /enrollments/recent
Authorization: Bearer <jwt-token>
```

### Assignment Management

#### Create Assignment
```http
POST /assignments/course/{courseId}
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "title": "Spring Boot Project",
  "description": "Create a RESTful API using Spring Boot",
  "dueDate": "2024-01-15T23:59:59",
  "maxScore": 100
}
```

**Response:**
```json
{
  "id": 1,
  "title": "Spring Boot Project",
  "description": "Create a RESTful API using Spring Boot",
  "dueDate": "2024-01-15T23:59:59",
  "maxScore": 100,
  "isActive": true,
  "createdAt": "2024-01-01T10:00:00",
  "courseId": 1,
  "courseTitle": "Introduction to Spring Boot",
  "isOverdue": false,
  "isDueSoon": false
}
```

#### Get All Active Assignments
```http
GET /assignments
Authorization: Bearer <jwt-token>
```

#### Get Assignment by ID
```http
GET /assignments/{id}
Authorization: Bearer <jwt-token>
```

#### Get Assignments by Course
```http
GET /assignments/course/{courseId}
Authorization: Bearer <jwt-token>
```

#### Search Assignments by Title
```http
GET /assignments/search?title=Project
Authorization: Bearer <jwt-token>
```

#### Get Assignments Due Soon
```http
GET /assignments/due-soon
Authorization: Bearer <jwt-token>
```

#### Get Overdue Assignments
```http
GET /assignments/overdue
Authorization: Bearer <jwt-token>
```

#### Get Assignments by Date Range
```http
GET /assignments/date-range?startDate=2024-01-01T00:00:00&endDate=2024-01-31T23:59:59
Authorization: Bearer <jwt-token>
```

#### Get Assignments with High Score
```http
GET /assignments/high-score?minScore=50
Authorization: Bearer <jwt-token>
```

#### Update Assignment
```http
PUT /assignments/{id}
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "title": "Updated Spring Boot Project",
  "description": "Updated project description",
  "dueDate": "2024-01-20T23:59:59",
  "maxScore": 100
}
```

#### Deactivate Assignment
```http
DELETE /assignments/{id}
Authorization: Bearer <jwt-token>
```

### Submission Management

#### Submit Assignment
```http
POST /submissions/submit
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "assignmentId": 1,
  "content": "This is my assignment submission",
  "fileUrl": "https://example.com/file.pdf"
}
```

**Response:**
```json
{
  "id": 1,
  "content": "This is my assignment submission",
  "fileUrl": "https://example.com/file.pdf",
  "submittedAt": "2024-01-01T10:00:00",
  "status": "SUBMITTED",
  "score": null,
  "feedback": null,
  "gradedAt": null,
  "gradedBy": null,
  "assignmentId": 1,
  "assignmentTitle": "Spring Boot Project",
  "studentId": 1,
  "studentName": "Alice Johnson",
  "isLate": false,
  "isGraded": false,
  "percentage": null
}
```

#### Get Submission by ID
```http
GET /submissions/{id}
Authorization: Bearer <jwt-token>
```

#### Get Submissions by Student
```http
GET /submissions/student/{studentId}
Authorization: Bearer <jwt-token>
```

#### Get Submissions by Assignment
```http
GET /submissions/assignment/{assignmentId}
Authorization: Bearer <jwt-token>
```

#### Get Submissions by Status
```http
GET /submissions/status/{status}
Authorization: Bearer <jwt-token>
```

#### Get Submissions by Student and Status
```http
GET /submissions/student/{studentId}/status/{status}
Authorization: Bearer <jwt-token>
```

#### Get Submissions by Assignment and Status
```http
GET /submissions/assignment/{assignmentId}/status/{status}
Authorization: Bearer <jwt-token>
```

#### Grade Submission
```http
POST /submissions/{submissionId}/grade?score=85&feedback=Great work!
Authorization: Bearer <jwt-token>
```

#### Get Late Submissions
```http
GET /submissions/late
Authorization: Bearer <jwt-token>
```

#### Get Submissions with High Score
```http
GET /submissions/high-score?minScore=80
Authorization: Bearer <jwt-token>
```

#### Get Recent Submissions
```http
GET /submissions/recent
Authorization: Bearer <jwt-token>
```

## Error Responses

### 400 Bad Request
```json
{
  "error": "Validation failed",
  "message": "Field validation error",
  "timestamp": "2024-01-01T10:00:00"
}
```

### 401 Unauthorized
```json
{
  "error": "Unauthorized",
  "message": "Invalid token",
  "timestamp": "2024-01-01T10:00:00"
}
```

### 403 Forbidden
```json
{
  "error": "Forbidden",
  "message": "Access denied",
  "timestamp": "2024-01-01T10:00:00"
}
```

### 404 Not Found
```json
{
  "error": "Not Found",
  "message": "Resource not found",
  "timestamp": "2024-01-01T10:00:00"
}
```

### 500 Internal Server Error
```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "timestamp": "2024-01-01T10:00:00"
}
```

## Status Codes

- `200 OK`: Request successful
- `201 Created`: Resource created successfully
- `400 Bad Request`: Invalid request data
- `401 Unauthorized`: Authentication required
- `403 Forbidden`: Access denied
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server error

## Data Types

### UserRole
- `STUDENT`
- `INSTRUCTOR`

### AssignmentStatus
- `PENDING`
- `SUBMITTED`
- `GRADED`
- `LATE`

## Rate Limiting

Currently, no rate limiting is implemented. Consider implementing rate limiting for production use.

## CORS

CORS is configured to allow all origins for development. Configure appropriately for production.