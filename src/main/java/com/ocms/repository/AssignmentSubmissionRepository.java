package com.ocms.repository;

import com.ocms.entity.Assignment;
import com.ocms.entity.AssignmentSubmission;
import com.ocms.entity.User;
import com.ocms.enums.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for AssignmentSubmission entity operations
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {
    
    /**
     * Find submission by student and assignment
     * 
     * @param student the student
     * @param assignment the assignment
     * @return Optional containing the submission if found
     */
    Optional<AssignmentSubmission> findByStudentAndAssignment(User student, Assignment assignment);
    
    /**
     * Find all submissions by student
     * 
     * @param student the student to filter by
     * @return List of submissions by the student
     */
    List<AssignmentSubmission> findByStudent(User student);
    
    /**
     * Find all submissions by assignment
     * 
     * @param assignment the assignment to filter by
     * @return List of submissions for the assignment
     */
    List<AssignmentSubmission> findByAssignment(Assignment assignment);
    
    /**
     * Find submissions by status
     * 
     * @param status the status to filter by
     * @return List of submissions with the specified status
     */
    List<AssignmentSubmission> findByStatus(AssignmentStatus status);
    
    /**
     * Find submissions by student and status
     * 
     * @param student the student to filter by
     * @param status the status to filter by
     * @return List of submissions matching the criteria
     */
    List<AssignmentSubmission> findByStudentAndStatus(User student, AssignmentStatus status);
    
    /**
     * Find submissions by assignment and status
     * 
     * @param assignment the assignment to filter by
     * @param status the status to filter by
     * @return List of submissions matching the criteria
     */
    List<AssignmentSubmission> findByAssignmentAndStatus(Assignment assignment, AssignmentStatus status);
    
    /**
     * Find graded submissions by student
     * 
     * @param student the student to filter by
     * @return List of graded submissions by the student
     */
    List<AssignmentSubmission> findByStudentAndStatus(User student, AssignmentStatus status);
    
    /**
     * Find late submissions
     * 
     * @return List of late submissions
     */
    @Query("SELECT s FROM AssignmentSubmission s WHERE s.status = 'LATE'")
    List<AssignmentSubmission> findLateSubmissions();
    
    /**
     * Find submissions by submission date range
     * 
     * @param startDate start date
     * @param endDate end date
     * @return List of submissions within the date range
     */
    List<AssignmentSubmission> findBySubmittedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Count submissions by assignment
     * 
     * @param assignment the assignment to count submissions for
     * @return number of submissions for the assignment
     */
    long countByAssignment(Assignment assignment);
    
    /**
     * Count submissions by assignment and status
     * 
     * @param assignment the assignment to count submissions for
     * @param status the status to filter by
     * @return number of submissions matching the criteria
     */
    long countByAssignmentAndStatus(Assignment assignment, AssignmentStatus status);
    
    /**
     * Find submissions with scores above threshold
     * 
     * @param minScore minimum score threshold
     * @return List of submissions with scores above threshold
     */
    @Query("SELECT s FROM AssignmentSubmission s WHERE s.score >= :minScore")
    List<AssignmentSubmission> findSubmissionsWithHighScore(@Param("minScore") Integer minScore);
    
    /**
     * Find recent submissions (last 7 days)
     * 
     * @return List of recent submissions
     */
    @Query("SELECT s FROM AssignmentSubmission s WHERE s.submittedAt >= CURRENT_DATE - 7")
    List<AssignmentSubmission> findRecentSubmissions();
}