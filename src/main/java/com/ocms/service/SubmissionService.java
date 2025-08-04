package com.ocms.service;

import com.ocms.dto.SubmissionDto;
import com.ocms.dto.SubmissionResponseDto;
import com.ocms.entity.Assignment;
import com.ocms.entity.AssignmentSubmission;
import com.ocms.entity.User;
import com.ocms.enums.AssignmentStatus;
import com.ocms.enums.UserRole;
import com.ocms.repository.AssignmentSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for assignment submission-related operations
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Service
public class SubmissionService {
    
    @Autowired
    private AssignmentSubmissionRepository submissionRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AssignmentService assignmentService;
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    /**
     * Submit an assignment
     * 
     * @param submissionDto the submission data
     * @param studentId the student ID
     * @return the submission response
     */
    public SubmissionResponseDto submitAssignment(SubmissionDto submissionDto, Long studentId) {
        User student = userService.getUserEntityById(studentId);
        Assignment assignment = assignmentService.getAssignmentEntityById(submissionDto.getAssignmentId());
        
        // Check if user is a student
        if (student.getRole() != UserRole.STUDENT) {
            throw new RuntimeException("Only students can submit assignments");
        }
        
        // Check if student is enrolled in the course
        if (!enrollmentService.isStudentEnrolled(studentId, assignment.getCourse().getId())) {
            throw new RuntimeException("Student is not enrolled in this course");
        }
        
        // Check if assignment is active
        if (!assignment.getIsActive()) {
            throw new RuntimeException("Assignment is not active");
        }
        
        // Check if student has already submitted
        if (submissionRepository.findByStudentAndAssignment(student, assignment).isPresent()) {
            throw new RuntimeException("Student has already submitted this assignment");
        }
        
        AssignmentSubmission submission = new AssignmentSubmission();
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setContent(submissionDto.getContent());
        submission.setFileUrl(submissionDto.getFileUrl());
        
        AssignmentSubmission savedSubmission = submissionRepository.save(submission);
        return convertToResponseDto(savedSubmission);
    }
    
    /**
     * Get submission by ID
     * 
     * @param id the submission ID
     * @return the submission response
     */
    public SubmissionResponseDto getSubmissionById(Long id) {
        AssignmentSubmission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found with id: " + id));
        return convertToResponseDto(submission);
    }
    
    /**
     * Get all submissions by student
     * 
     * @param studentId the student ID
     * @return list of submissions by the student
     */
    public List<SubmissionResponseDto> getSubmissionsByStudent(Long studentId) {
        User student = userService.getUserEntityById(studentId);
        return submissionRepository.findByStudent(student).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get all submissions for an assignment
     * 
     * @param assignmentId the assignment ID
     * @return list of submissions for the assignment
     */
    public List<SubmissionResponseDto> getSubmissionsByAssignment(Long assignmentId) {
        Assignment assignment = assignmentService.getAssignmentEntityById(assignmentId);
        return submissionRepository.findByAssignment(assignment).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get submissions by status
     * 
     * @param status the status to filter by
     * @return list of submissions with the specified status
     */
    public List<SubmissionResponseDto> getSubmissionsByStatus(AssignmentStatus status) {
        return submissionRepository.findByStatus(status).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get submissions by student and status
     * 
     * @param studentId the student ID
     * @param status the status to filter by
     * @return list of submissions matching the criteria
     */
    public List<SubmissionResponseDto> getSubmissionsByStudentAndStatus(Long studentId, AssignmentStatus status) {
        User student = userService.getUserEntityById(studentId);
        return submissionRepository.findByStudentAndStatus(student, status).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get submissions by assignment and status
     * 
     * @param assignmentId the assignment ID
     * @param status the status to filter by
     * @return list of submissions matching the criteria
     */
    public List<SubmissionResponseDto> getSubmissionsByAssignmentAndStatus(Long assignmentId, AssignmentStatus status) {
        Assignment assignment = assignmentService.getAssignmentEntityById(assignmentId);
        return submissionRepository.findByAssignmentAndStatus(assignment, status).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Grade a submission
     * 
     * @param submissionId the submission ID
     * @param score the score
     * @param feedback the feedback
     * @param instructorId the instructor ID (for authorization)
     * @return the graded submission response
     */
    public SubmissionResponseDto gradeSubmission(Long submissionId, Integer score, String feedback, Long instructorId) {
        AssignmentSubmission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found with id: " + submissionId));
        
        // Check if the user is the instructor of this course
        if (!submission.getAssignment().getCourse().getInstructor().getId().equals(instructorId)) {
            throw new RuntimeException("Only the course instructor can grade this submission");
        }
        
        // Check if submission is already graded
        if (submission.isGraded()) {
            throw new RuntimeException("Submission is already graded");
        }
        
        submission.grade(score, feedback, instructorId);
        AssignmentSubmission savedSubmission = submissionRepository.save(submission);
        return convertToResponseDto(savedSubmission);
    }
    
    /**
     * Get late submissions
     * 
     * @return list of late submissions
     */
    public List<SubmissionResponseDto> getLateSubmissions() {
        return submissionRepository.findLateSubmissions().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get submissions with high scores
     * 
     * @param minScore minimum score threshold
     * @return list of submissions with high scores
     */
    public List<SubmissionResponseDto> getSubmissionsWithHighScore(Integer minScore) {
        return submissionRepository.findSubmissionsWithHighScore(minScore).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get recent submissions (last 7 days)
     * 
     * @return list of recent submissions
     */
    public List<SubmissionResponseDto> getRecentSubmissions() {
        return submissionRepository.findRecentSubmissions().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Convert submission entity to response DTO
     * 
     * @param submission the submission entity
     * @return the submission response DTO
     */
    private SubmissionResponseDto convertToResponseDto(AssignmentSubmission submission) {
        SubmissionResponseDto dto = new SubmissionResponseDto();
        dto.setId(submission.getId());
        dto.setContent(submission.getContent());
        dto.setFileUrl(submission.getFileUrl());
        dto.setSubmittedAt(submission.getSubmittedAt());
        dto.setStatus(submission.getStatus());
        dto.setScore(submission.getScore());
        dto.setFeedback(submission.getFeedback());
        dto.setGradedAt(submission.getGradedAt());
        dto.setGradedBy(submission.getGradedBy());
        dto.setAssignmentId(submission.getAssignment().getId());
        dto.setAssignmentTitle(submission.getAssignment().getTitle());
        dto.setStudentId(submission.getStudent().getId());
        dto.setStudentName(submission.getStudent().getFullName());
        dto.setIsLate(submission.isLate());
        dto.setIsGraded(submission.isGraded());
        dto.setPercentage(submission.getPercentage());
        
        return dto;
    }
}