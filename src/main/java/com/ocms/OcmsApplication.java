package com.ocms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Online Course Management System (OCMS)
 * 
 * This Spring Boot application provides a comprehensive backend for managing
 * online courses, users, enrollments, assignments, and submissions.
 * 
 * Features:
 * - User Management (Students and Instructors)
 * - Course Management
 * - Enrollment System
 * - Assignment Management
 * - Reporting System
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@SpringBootApplication
public class OcmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcmsApplication.class, args);
        System.out.println("🚀 Online Course Management System (OCMS) is running!");
        System.out.println("📚 API Documentation: http://localhost:8080/api/v1");
        System.out.println("🗄️  H2 Console: http://localhost:8080/h2-console");
    }
}