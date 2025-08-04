package com.ocms.controller;

import com.ocms.dto.CourseDto;
import com.ocms.dto.CourseResponseDto;
import com.ocms.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for course management endpoints
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    /**
     * Create a new course
     * 
     * @param courseDto the course data
     * @return the created course response
     */
    @PostMapping
    public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseDto courseDto) {
        try {
            // Get current user ID from authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long instructorId = getCurrentUserId();
            
            CourseResponseDto course = courseService.createCourse(courseDto, instructorId);
            return ResponseEntity.status(HttpStatus.CREATED).body(course);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get all active courses
     * 
     * @return list of all active courses
     */
    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAllActiveCourses() {
        try {
            List<CourseResponseDto> courses = courseService.getAllActiveCourses();
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get course by ID
     * 
     * @param id the course ID
     * @return the course response
     */
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id) {
        try {
            CourseResponseDto course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get courses by instructor
     * 
     * @return list of courses by the current instructor
     */
    @GetMapping("/my-courses")
    public ResponseEntity<List<CourseResponseDto>> getMyCourses() {
        try {
            Long instructorId = getCurrentUserId();
            List<CourseResponseDto> courses = courseService.getCoursesByInstructor(instructorId);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Search courses by title
     * 
     * @param title the title to search for
     * @return list of courses matching the title
     */
    @GetMapping("/search/title")
    public ResponseEntity<List<CourseResponseDto>> searchCoursesByTitle(@RequestParam String title) {
        try {
            List<CourseResponseDto> courses = courseService.searchCoursesByTitle(title);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Search courses by description
     * 
     * @param description the description to search for
     * @return list of courses matching the description
     */
    @GetMapping("/search/description")
    public ResponseEntity<List<CourseResponseDto>> searchCoursesByDescription(@RequestParam String description) {
        try {
            List<CourseResponseDto> courses = courseService.searchCoursesByDescription(description);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get available courses (not full)
     * 
     * @return list of available courses
     */
    @GetMapping("/available")
    public ResponseEntity<List<CourseResponseDto>> getAvailableCourses() {
        try {
            List<CourseResponseDto> courses = courseService.getAvailableCourses();
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get courses by duration range
     * 
     * @param minDuration minimum duration in hours
     * @param maxDuration maximum duration in hours
     * @return list of courses within the duration range
     */
    @GetMapping("/duration")
    public ResponseEntity<List<CourseResponseDto>> getCoursesByDurationRange(
            @RequestParam Integer minDuration, 
            @RequestParam Integer maxDuration) {
        try {
            List<CourseResponseDto> courses = courseService.getCoursesByDurationRange(minDuration, maxDuration);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get courses with high enrollment
     * 
     * @param minEnrollment minimum number of enrolled students
     * @return list of courses with high enrollment
     */
    @GetMapping("/popular")
    public ResponseEntity<List<CourseResponseDto>> getCoursesWithHighEnrollment(
            @RequestParam(defaultValue = "5") int minEnrollment) {
        try {
            List<CourseResponseDto> courses = courseService.getCoursesWithHighEnrollment(minEnrollment);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Update course
     * 
     * @param id the course ID
     * @param courseDto the updated course data
     * @return the updated course response
     */
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable Long id, 
                                                        @Valid @RequestBody CourseDto courseDto) {
        try {
            Long instructorId = getCurrentUserId();
            CourseResponseDto updatedCourse = courseService.updateCourse(id, courseDto, instructorId);
            return ResponseEntity.ok(updatedCourse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Deactivate course
     * 
     * @param id the course ID
     * @return success response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateCourse(@PathVariable Long id) {
        try {
            Long instructorId = getCurrentUserId();
            courseService.deactivateCourse(id, instructorId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Helper method to get current user ID from authentication
     * 
     * @return the current user ID
     */
    private Long getCurrentUserId() {
        // This is a simplified implementation
        // In a real application, you would extract the user ID from the JWT token
        // For now, we'll return a default instructor ID
        return 1L; // This should be extracted from the JWT token
    }
}