package com.ocms.service;

import com.ocms.dto.UserResponseDto;
import com.ocms.entity.Course;
import com.ocms.entity.Enrollment;
import com.ocms.entity.User;
import com.ocms.enums.UserRole;
import com.ocms.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for enrollment-related operations
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Service
public class EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CourseService courseService;
    
    /**
     * Enroll a student in a course
     * 
     * @param studentId the student ID
     * @param courseId the course ID
     * @return the enrollment response
     */
    public String enrollStudent(Long studentId, Long courseId) {
        User student = userService.getUserEntityById(studentId);
        Course course = courseService.getCourseEntityById(courseId);
        
        // Check if student is already enrolled
        if (enrollmentRepository.existsByStudentAndCourseAndIsActiveTrue(student, course)) {
            throw new RuntimeException("Student is already enrolled in this course");
        }
        
        // Check if course is full
        if (course.isFull()) {
            throw new RuntimeException("Course is full and cannot accept more students");
        }
        
        // Check if course is active
        if (!course.getIsActive()) {
            throw new RuntimeException("Course is not active");
        }
        
        // Check if user is a student
        if (student.getRole() != UserRole.STUDENT) {
            throw new RuntimeException("Only students can enroll in courses");
        }
        
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setIsActive(true);
        
        enrollmentRepository.save(enrollment);
        
        return "Student " + student.getFullName() + " successfully enrolled in " + course.getTitle();
    }
    
    /**
     * Drop a student from a course
     * 
     * @param studentId the student ID
     * @param courseId the course ID
     * @return the drop response
     */
    public String dropStudent(Long studentId, Long courseId) {
        User student = userService.getUserEntityById(studentId);
        Course course = courseService.getCourseEntityById(courseId);
        
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, course)
                .orElseThrow(() -> new RuntimeException("Student is not enrolled in this course"));
        
        if (!enrollment.getIsActive()) {
            throw new RuntimeException("Student has already dropped this course");
        }
        
        enrollment.drop();
        enrollmentRepository.save(enrollment);
        
        return "Student " + student.getFullName() + " successfully dropped from " + course.getTitle();
    }
    
    /**
     * Get all enrollments for a student
     * 
     * @param studentId the student ID
     * @return list of enrollments for the student
     */
    public List<EnrollmentResponse> getEnrollmentsByStudent(Long studentId) {
        User student = userService.getUserEntityById(studentId);
        return enrollmentRepository.findByStudentAndIsActiveTrue(student).stream()
                .map(this::convertToEnrollmentResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Get all enrollments for a course
     * 
     * @param courseId the course ID
     * @return list of enrollments for the course
     */
    public List<EnrollmentResponse> getEnrollmentsByCourse(Long courseId) {
        Course course = courseService.getCourseEntityById(courseId);
        return enrollmentRepository.findByCourseAndIsActiveTrue(course).stream()
                .map(this::convertToEnrollmentResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Check if student is enrolled in course
     * 
     * @param studentId the student ID
     * @param courseId the course ID
     * @return true if enrolled, false otherwise
     */
    public boolean isStudentEnrolled(Long studentId, Long courseId) {
        User student = userService.getUserEntityById(studentId);
        Course course = courseService.getCourseEntityById(courseId);
        return enrollmentRepository.existsByStudentAndCourseAndIsActiveTrue(student, course);
    }
    
    /**
     * Get enrollment count for a course
     * 
     * @param courseId the course ID
     * @return the number of enrolled students
     */
    public long getEnrollmentCount(Long courseId) {
        Course course = courseService.getCourseEntityById(courseId);
        return enrollmentRepository.countByCourseAndIsActiveTrue(course);
    }
    
    /**
     * Get recent enrollments (last 30 days)
     * 
     * @return list of recent enrollments
     */
    public List<EnrollmentResponse> getRecentEnrollments() {
        return enrollmentRepository.findRecentEnrollments().stream()
                .map(this::convertToEnrollmentResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Convert enrollment entity to response
     * 
     * @param enrollment the enrollment entity
     * @return the enrollment response
     */
    private EnrollmentResponse convertToEnrollmentResponse(Enrollment enrollment) {
        EnrollmentResponse response = new EnrollmentResponse();
        response.setId(enrollment.getId());
        response.setEnrolledAt(enrollment.getEnrolledAt());
        response.setIsActive(enrollment.getIsActive());
        response.setDroppedAt(enrollment.getDroppedAt());
        
        // Set student info
        UserResponseDto studentDto = new UserResponseDto();
        studentDto.setId(enrollment.getStudent().getId());
        studentDto.setFirstName(enrollment.getStudent().getFirstName());
        studentDto.setLastName(enrollment.getStudent().getLastName());
        studentDto.setEmail(enrollment.getStudent().getEmail());
        studentDto.setFullName(enrollment.getStudent().getFullName());
        response.setStudent(studentDto);
        
        // Set course info
        response.setCourseId(enrollment.getCourse().getId());
        response.setCourseTitle(enrollment.getCourse().getTitle());
        
        return response;
    }
    
    /**
     * Inner class for enrollment response
     */
    public static class EnrollmentResponse {
        private Long id;
        private UserResponseDto student;
        private Long courseId;
        private String courseTitle;
        private java.time.LocalDateTime enrolledAt;
        private Boolean isActive;
        private java.time.LocalDateTime droppedAt;
        
        // Getters and setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public UserResponseDto getStudent() { return student; }
        public void setStudent(UserResponseDto student) { this.student = student; }
        
        public Long getCourseId() { return courseId; }
        public void setCourseId(Long courseId) { this.courseId = courseId; }
        
        public String getCourseTitle() { return courseTitle; }
        public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }
        
        public java.time.LocalDateTime getEnrolledAt() { return enrolledAt; }
        public void setEnrolledAt(java.time.LocalDateTime enrolledAt) { this.enrolledAt = enrolledAt; }
        
        public Boolean getIsActive() { return isActive; }
        public void setIsActive(Boolean isActive) { this.isActive = isActive; }
        
        public java.time.LocalDateTime getDroppedAt() { return droppedAt; }
        public void setDroppedAt(java.time.LocalDateTime droppedAt) { this.droppedAt = droppedAt; }
    }
}