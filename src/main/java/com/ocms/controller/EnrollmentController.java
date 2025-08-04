package com.ocms.controller;

import com.ocms.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for enrollment-related endpoints
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    /**
     * Enroll a student in a course
     * 
     * @param studentId the student ID
     * @param courseId the course ID
     * @return enrollment response
     */
    @PostMapping("/enroll")
    public ResponseEntity<String> enrollStudent(@RequestParam Long studentId, 
                                              @RequestParam Long courseId) {
        try {
            String result = enrollmentService.enrollStudent(studentId, courseId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Drop a student from a course
     * 
     * @param studentId the student ID
     * @param courseId the course ID
     * @return drop response
     */
    @PostMapping("/drop")
    public ResponseEntity<String> dropStudent(@RequestParam Long studentId, 
                                            @RequestParam Long courseId) {
        try {
            String result = enrollmentService.dropStudent(studentId, courseId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Get all enrollments for a student
     * 
     * @param studentId the student ID
     * @return list of enrollments for the student
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentService.EnrollmentResponse>> getEnrollmentsByStudent(
            @PathVariable Long studentId) {
        try {
            List<EnrollmentService.EnrollmentResponse> enrollments = 
                enrollmentService.getEnrollmentsByStudent(studentId);
            return ResponseEntity.ok(enrollments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get all enrollments for a course
     * 
     * @param courseId the course ID
     * @return list of enrollments for the course
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<EnrollmentService.EnrollmentResponse>> getEnrollmentsByCourse(
            @PathVariable Long courseId) {
        try {
            List<EnrollmentService.EnrollmentResponse> enrollments = 
                enrollmentService.getEnrollmentsByCourse(courseId);
            return ResponseEntity.ok(enrollments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Check if student is enrolled in course
     * 
     * @param studentId the student ID
     * @param courseId the course ID
     * @return true if enrolled, false otherwise
     */
    @GetMapping("/check")
    public ResponseEntity<Boolean> isStudentEnrolled(@RequestParam Long studentId, 
                                                   @RequestParam Long courseId) {
        try {
            boolean isEnrolled = enrollmentService.isStudentEnrolled(studentId, courseId);
            return ResponseEntity.ok(isEnrolled);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get enrollment count for a course
     * 
     * @param courseId the course ID
     * @return the number of enrolled students
     */
    @GetMapping("/count/{courseId}")
    public ResponseEntity<Long> getEnrollmentCount(@PathVariable Long courseId) {
        try {
            long count = enrollmentService.getEnrollmentCount(courseId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get recent enrollments (last 30 days)
     * 
     * @return list of recent enrollments
     */
    @GetMapping("/recent")
    public ResponseEntity<List<EnrollmentService.EnrollmentResponse>> getRecentEnrollments() {
        try {
            List<EnrollmentService.EnrollmentResponse> enrollments = 
                enrollmentService.getRecentEnrollments();
            return ResponseEntity.ok(enrollments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}