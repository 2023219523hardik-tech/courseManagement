# Online Course Management System (OCMS)

A comprehensive Spring Boot backend application for managing online courses, users, enrollments, assignments, and submissions.

## 🚀 Features

### User Management Module
- **User Registration & Authentication**: Register and manage users (Students, Instructors)
- **Role-based Access Control**: Using enums (STUDENT, INSTRUCTOR)
- **JWT Authentication**: Secure token-based authentication
- **User CRUD Operations**: Complete user management functionality

### Course Management Module
- **Course Creation**: Instructors can create, update, delete, and view courses
- **Course Enrollment**: Students can view and enroll in courses
- **Course Search**: Search courses by title, description, duration
- **Course Status**: Track course availability and enrollment limits

### Enrollment Module
- **Student Enrollment**: Students can enroll in or drop courses
- **Enrollment Tracking**: Monitor enrollment status and history
- **Instructor View**: Instructors can see enrolled students

### Assignment Module
- **Assignment Creation**: Instructors can create and assign assignments per course
- **Assignment Submission**: Students can submit assignments with content or file URLs
- **Submission Tracking**: Track submission date, status, and grading
- **Due Date Management**: Monitor assignments due soon and overdue

### Reporting Module (Optional)
- **Student Reports**: Generate reports per student (list of courses, assignments submitted)
- **Analytics**: Track enrollment trends, assignment completion rates

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: H2 (Development), MySQL (Production)
- **ORM**: Spring Data JPA / Hibernate
- **Security**: Spring Security with JWT
- **Validation**: Bean Validation
- **Build Tool**: Maven
- **Documentation**: Javadoc

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- MySQL (for production) or H2 (for development)

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd online-course-management-system
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Access H2 Console (Development)
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:ocmsdb`
- Username: `sa`
- Password: (leave empty)

## 📚 API Documentation

### Authentication Endpoints

#### Register User
```http
POST /api/v1/auth/register
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

#### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "email": "john.doe@example.com",
  "password": "password123"
}
```

#### Get Current User
```http
GET /api/v1/auth/me
Authorization: Bearer <jwt-token>
```

### User Management Endpoints

#### Get All Users
```http
GET /api/v1/users
Authorization: Bearer <jwt-token>
```

#### Get User by ID
```http
GET /api/v1/users/{id}
Authorization: Bearer <jwt-token>
```

#### Update User
```http
PUT /api/v1/users/{id}
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

### Course Management Endpoints

#### Create Course
```http
POST /api/v1/courses
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "title": "Introduction to Spring Boot",
  "description": "Learn the fundamentals of Spring Boot framework",
  "duration": 40,
  "maxStudents": 30
}
```

#### Get All Active Courses
```http
GET /api/v1/courses
Authorization: Bearer <jwt-token>
```

#### Get Course by ID
```http
GET /api/v1/courses/{id}
Authorization: Bearer <jwt-token>
```

#### Search Courses by Title
```http
GET /api/v1/courses/search/title?title=Spring
Authorization: Bearer <jwt-token>
```

### Enrollment Endpoints

#### Enroll Student
```http
POST /api/v1/enrollments/enroll?studentId=1&courseId=1
Authorization: Bearer <jwt-token>
```

#### Drop Course
```http
POST /api/v1/enrollments/drop?studentId=1&courseId=1
Authorization: Bearer <jwt-token>
```

#### Get Enrollments by Student
```http
GET /api/v1/enrollments/student/{studentId}
Authorization: Bearer <jwt-token>
```

### Assignment Endpoints

#### Create Assignment
```http
POST /api/v1/assignments/course/{courseId}
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "title": "Spring Boot Project",
  "description": "Create a RESTful API using Spring Boot",
  "dueDate": "2024-01-15T23:59:59",
  "maxScore": 100
}
```

#### Get Assignments by Course
```http
GET /api/v1/assignments/course/{courseId}
Authorization: Bearer <jwt-token>
```

#### Get Assignments Due Soon
```http
GET /api/v1/assignments/due-soon
Authorization: Bearer <jwt-token>
```

### Submission Endpoints

#### Submit Assignment
```http
POST /api/v1/submissions/submit
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "assignmentId": 1,
  "content": "This is my assignment submission",
  "fileUrl": "https://example.com/file.pdf"
}
```

#### Grade Submission
```http
POST /api/v1/submissions/{submissionId}/grade?score=85&feedback=Great work!
Authorization: Bearer <jwt-token>
```

#### Get Submissions by Student
```http
GET /api/v1/submissions/student/{studentId}
Authorization: Bearer <jwt-token>
```

## 🗄️ Database Schema

### Users Table
- `id` (Primary Key)
- `first_name`
- `last_name`
- `email` (Unique)
- `password`
- `role` (STUDENT/INSTRUCTOR)
- `phone_number`
- `created_at`
- `updated_at`
- `is_active`

### Courses Table
- `id` (Primary Key)
- `title`
- `description`
- `duration`
- `max_students`
- `instructor_id` (Foreign Key)
- `is_active`
- `created_at`
- `updated_at`

### Enrollments Table
- `id` (Primary Key)
- `student_id` (Foreign Key)
- `course_id` (Foreign Key)
- `enrolled_at`
- `is_active`
- `dropped_at`

### Assignments Table
- `id` (Primary Key)
- `title`
- `description`
- `due_date`
- `max_score`
- `course_id` (Foreign Key)
- `is_active`
- `created_at`
- `updated_at`

### Assignment Submissions Table
- `id` (Primary Key)
- `student_id` (Foreign Key)
- `assignment_id` (Foreign Key)
- `content`
- `file_url`
- `submitted_at`
- `status` (PENDING/SUBMITTED/GRADED/LATE)
- `score`
- `feedback`
- `graded_at`
- `graded_by`

## 🔧 Configuration

### Application Properties
The application uses `application.yml` for configuration:

```yaml
spring:
  application:
    name: Online Course Management System
  datasource:
    url: jdbc:h2:mem:ocmsdb
    driver-class-name: org.h2.Driver
    username: sa
    password: 
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true

jwt:
  secret: your-secret-key
  expiration: 86400000

server:
  port: 8080
  servlet:
    context-path: /api/v1
```

### Production Configuration
For production, update the database configuration:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ocms
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your-username
    password: your-password
  jpa:
    hibernate:
      ddl-auto: update
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Test Coverage
```bash
mvn jacoco:report
```

## 📦 Deployment

### Docker Deployment
```bash
# Build Docker image
docker build -t ocms .

# Run container
docker run -p 8080:8080 ocms
```

### Traditional Deployment
```bash
# Build JAR
mvn clean package

# Run JAR
java -jar target/online-course-management-system-1.0.0.jar
```

## 🔒 Security

- **JWT Authentication**: Secure token-based authentication
- **Role-based Authorization**: Different permissions for students and instructors
- **Password Encryption**: BCrypt password hashing
- **Input Validation**: Bean validation for all inputs
- **CORS Configuration**: Cross-origin resource sharing setup

## 📈 Monitoring & Logging

- **Application Logs**: Configured logging levels for different packages
- **SQL Logging**: Hibernate SQL query logging (development)
- **Error Handling**: Comprehensive exception handling

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **OCMS Team** - *Initial work*

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- JWT.io for JWT implementation guidance
- H2 Database for the in-memory database

## 📞 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation

---

**Note**: This is a comprehensive Spring Boot backend for an Online Course Management System. The system is designed to be scalable, secure, and follows best practices for enterprise applications.