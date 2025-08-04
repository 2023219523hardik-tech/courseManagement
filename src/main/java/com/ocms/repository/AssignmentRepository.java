package com.ocms.repository;

import com.ocms.entity.Assignment;
import com.ocms.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Assignment entity operations
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    /**
     * Find all active assignments
     * 
     * @return List of active assignments
     */
    List<Assignment> findByIsActiveTrue();
    
    /**
     * Find assignments by course
     * 
     * @param course the course to filter by
     * @return List of assignments for the course
     */
    List<Assignment> findByCourse(Course course);
    
    /**
     * Find active assignments by course
     * 
     * @param course the course to filter by
     * @return List of active assignments for the course
     */
    List<Assignment> findByCourseAndIsActiveTrue(Course course);
    
    /**
     * Find assignments by title containing the search term
     * 
     * @param title the title to search for
     * @return List of assignments with matching title
     */
    List<Assignment> findByTitleContainingIgnoreCaseAndIsActiveTrue(String title);
    
    /**
     * Find assignments due soon (within next 24 hours)
     * 
     * @return List of assignments due soon
     */
    @Query("SELECT a FROM Assignment a WHERE a.isActive = true AND a.dueDate BETWEEN :now AND :tomorrow")
    List<Assignment> findAssignmentsDueSoon(@Param("now") LocalDateTime now, 
                                           @Param("tomorrow") LocalDateTime tomorrow);
    
    /**
     * Find overdue assignments
     * 
     * @return List of overdue assignments
     */
    @Query("SELECT a FROM Assignment a WHERE a.isActive = true AND a.dueDate < :now")
    List<Assignment> findOverdueAssignments(@Param("now") LocalDateTime now);
    
    /**
     * Find assignments by due date range
     * 
     * @param startDate start date
     * @param endDate end date
     * @return List of assignments within the due date range
     */
    List<Assignment> findByDueDateBetweenAndIsActiveTrue(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find assignments with high max score
     * 
     * @param minScore minimum max score
     * @return List of assignments with high max score
     */
    List<Assignment> findByMaxScoreGreaterThanAndIsActiveTrue(Integer minScore);
    
    /**
     * Count assignments by course
     * 
     * @param course the course to count assignments for
     * @return number of assignments for the course
     */
    long countByCourseAndIsActiveTrue(Course course);
}