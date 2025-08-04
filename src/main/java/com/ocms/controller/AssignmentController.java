package com.ocms.controller;

import com.ocms.dto.AssignmentDto;
import com.ocms.dto.AssignmentResponseDto;
import com.ocms.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for assignment-related endpoints
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/assignments")
@CrossOrigin(origins = "*")
public class AssignmentController {
    
    @Autowired
    private AssignmentService assignmentService;
    
    /**
     * Create a new assignment
     * 
     * @param assignmentDto the assignment data
     * @param courseId the course ID
     * @return the created assignment response
     */
    @PostMapping("/course/{courseId}")
    public ResponseEntity<AssignmentResponseDto> createAssignment(
            @PathVariable Long courseId,
            @Valid @RequestBody AssignmentDto assignmentDto) {
        try {
            Long instructorId = getCurrentUserId();
            AssignmentResponseDto assignment = assignmentService.createAssignment(assignmentDto, courseId, instructorId);
            return ResponseEntity.status(HttpStatus.CREATED).body(assignment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get all active assignments
     * 
     * @return list of all active assignments
     */
    @GetMapping
    public ResponseEntity<List<AssignmentResponseDto>> getAllActiveAssignments() {
        try {
            List<AssignmentResponseDto> assignments = assignmentService.getAllActiveAssignments();
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get assignment by ID
     * 
     * @param id the assignment ID
     * @return the assignment response
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponseDto> getAssignmentById(@PathVariable Long id) {
        try {
            AssignmentResponseDto assignment = assignmentService.getAssignmentById(id);
            return ResponseEntity.ok(assignment);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get assignments by course
     * 
     * @param courseId the course ID
     * @return list of assignments for the course
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AssignmentResponseDto>> getAssignmentsByCourse(@PathVariable Long courseId) {
        try {
            List<AssignmentResponseDto> assignments = assignmentService.getAssignmentsByCourse(courseId);
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Search assignments by title
     * 
     * @param title the title to search for
     * @return list of assignments matching the title
     */
    @GetMapping("/search")
    public ResponseEntity<List<AssignmentResponseDto>> searchAssignmentsByTitle(@RequestParam String title) {
        try {
            List<AssignmentResponseDto> assignments = assignmentService.searchAssignmentsByTitle(title);
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get assignments due soon (within next 24 hours)
     * 
     * @return list of assignments due soon
     */
    @GetMapping("/due-soon")
    public ResponseEntity<List<AssignmentResponseDto>> getAssignmentsDueSoon() {
        try {
            List<AssignmentResponseDto> assignments = assignmentService.getAssignmentsDueSoon();
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get overdue assignments
     * 
     * @return list of overdue assignments
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<AssignmentResponseDto>> getOverdueAssignments() {
        try {
            List<AssignmentResponseDto> assignments = assignmentService.getOverdueAssignments();
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get assignments by due date range
     * 
     * @param startDate start date
     * @param endDate end date
     * @return list of assignments within the due date range
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<AssignmentResponseDto>> getAssignmentsByDueDateRange(
            @RequestParam LocalDateTime startDate, 
            @RequestParam LocalDateTime endDate) {
        try {
            List<AssignmentResponseDto> assignments = assignmentService.getAssignmentsByDueDateRange(startDate, endDate);
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get assignments with high max score
     * 
     * @param minScore minimum max score
     * @return list of assignments with high max score
     */
    @GetMapping("/high-score")
    public ResponseEntity<List<AssignmentResponseDto>> getAssignmentsWithHighScore(
            @RequestParam Integer minScore) {
        try {
            List<AssignmentResponseDto> assignments = assignmentService.getAssignmentsWithHighScore(minScore);
            return ResponseEntity.ok(assignments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Update assignment
     * 
     * @param id the assignment ID
     * @param assignmentDto the updated assignment data
     * @return the updated assignment response
     */
    @PutMapping("/{id}")
    public ResponseEntity<AssignmentResponseDto> updateAssignment(@PathVariable Long id, 
                                                               @Valid @RequestBody AssignmentDto assignmentDto) {
        try {
            Long instructorId = getCurrentUserId();
            AssignmentResponseDto updatedAssignment = assignmentService.updateAssignment(id, assignmentDto, instructorId);
            return ResponseEntity.ok(updatedAssignment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Deactivate assignment
     * 
     * @param id the assignment ID
     * @return success response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateAssignment(@PathVariable Long id) {
        try {
            Long instructorId = getCurrentUserId();
            assignmentService.deactivateAssignment(id, instructorId);
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