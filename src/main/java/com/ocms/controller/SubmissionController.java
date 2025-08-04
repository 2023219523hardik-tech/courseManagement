package com.ocms.controller;

import com.ocms.dto.SubmissionDto;
import com.ocms.dto.SubmissionResponseDto;
import com.ocms.enums.AssignmentStatus;
import com.ocms.service.SubmissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for assignment submission-related endpoints
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/submissions")
@CrossOrigin(origins = "*")
public class SubmissionController {
    
    @Autowired
    private SubmissionService submissionService;
    
    /**
     * Submit an assignment
     * 
     * @param submissionDto the submission data
     * @return the submission response
     */
    @PostMapping("/submit")
    public ResponseEntity<SubmissionResponseDto> submitAssignment(@Valid @RequestBody SubmissionDto submissionDto) {
        try {
            Long studentId = getCurrentUserId();
            SubmissionResponseDto submission = submissionService.submitAssignment(submissionDto, studentId);
            return ResponseEntity.status(HttpStatus.CREATED).body(submission);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get submission by ID
     * 
     * @param id the submission ID
     * @return the submission response
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResponseDto> getSubmissionById(@PathVariable Long id) {
        try {
            SubmissionResponseDto submission = submissionService.getSubmissionById(id);
            return ResponseEntity.ok(submission);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get all submissions by student
     * 
     * @param studentId the student ID
     * @return list of submissions by the student
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<SubmissionResponseDto>> getSubmissionsByStudent(@PathVariable Long studentId) {
        try {
            List<SubmissionResponseDto> submissions = submissionService.getSubmissionsByStudent(studentId);
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get all submissions for an assignment
     * 
     * @param assignmentId the assignment ID
     * @return list of submissions for the assignment
     */
    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<SubmissionResponseDto>> getSubmissionsByAssignment(@PathVariable Long assignmentId) {
        try {
            List<SubmissionResponseDto> submissions = submissionService.getSubmissionsByAssignment(assignmentId);
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get submissions by status
     * 
     * @param status the status to filter by
     * @return list of submissions with the specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<SubmissionResponseDto>> getSubmissionsByStatus(@PathVariable AssignmentStatus status) {
        try {
            List<SubmissionResponseDto> submissions = submissionService.getSubmissionsByStatus(status);
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get submissions by student and status
     * 
     * @param studentId the student ID
     * @param status the status to filter by
     * @return list of submissions matching the criteria
     */
    @GetMapping("/student/{studentId}/status/{status}")
    public ResponseEntity<List<SubmissionResponseDto>> getSubmissionsByStudentAndStatus(
            @PathVariable Long studentId, 
            @PathVariable AssignmentStatus status) {
        try {
            List<SubmissionResponseDto> submissions = submissionService.getSubmissionsByStudentAndStatus(studentId, status);
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get submissions by assignment and status
     * 
     * @param assignmentId the assignment ID
     * @param status the status to filter by
     * @return list of submissions matching the criteria
     */
    @GetMapping("/assignment/{assignmentId}/status/{status}")
    public ResponseEntity<List<SubmissionResponseDto>> getSubmissionsByAssignmentAndStatus(
            @PathVariable Long assignmentId, 
            @PathVariable AssignmentStatus status) {
        try {
            List<SubmissionResponseDto> submissions = submissionService.getSubmissionsByAssignmentAndStatus(assignmentId, status);
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Grade a submission
     * 
     * @param submissionId the submission ID
     * @param score the score
     * @param feedback the feedback
     * @return the graded submission response
     */
    @PostMapping("/{submissionId}/grade")
    public ResponseEntity<SubmissionResponseDto> gradeSubmission(
            @PathVariable Long submissionId,
            @RequestParam Integer score,
            @RequestParam String feedback) {
        try {
            Long instructorId = getCurrentUserId();
            SubmissionResponseDto submission = submissionService.gradeSubmission(submissionId, score, feedback, instructorId);
            return ResponseEntity.ok(submission);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get late submissions
     * 
     * @return list of late submissions
     */
    @GetMapping("/late")
    public ResponseEntity<List<SubmissionResponseDto>> getLateSubmissions() {
        try {
            List<SubmissionResponseDto> submissions = submissionService.getLateSubmissions();
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get submissions with high scores
     * 
     * @param minScore minimum score threshold
     * @return list of submissions with high scores
     */
    @GetMapping("/high-score")
    public ResponseEntity<List<SubmissionResponseDto>> getSubmissionsWithHighScore(
            @RequestParam Integer minScore) {
        try {
            List<SubmissionResponseDto> submissions = submissionService.getSubmissionsWithHighScore(minScore);
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get recent submissions (last 7 days)
     * 
     * @return list of recent submissions
     */
    @GetMapping("/recent")
    public ResponseEntity<List<SubmissionResponseDto>> getRecentSubmissions() {
        try {
            List<SubmissionResponseDto> submissions = submissionService.getRecentSubmissions();
            return ResponseEntity.ok(submissions);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
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
        // For now, we'll return a default user ID
        return 1L; // This should be extracted from the JWT token
    }
}