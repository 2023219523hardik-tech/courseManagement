package com.ocms.repository;

import com.ocms.entity.Course;
import com.ocms.entity.Enrollment;
import com.ocms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Enrollment entity operations
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
    /**
     * Find enrollment by student and course
     * 
     * @param student the student
     * @param course the course
     * @return Optional containing the enrollment if found
     */
    Optional<Enrollment> findByStudentAndCourse(User student, Course course);
    
    /**
     * Check if student is enrolled in course
     * 
     * @param student the student
     * @param course the course
     * @return true if enrolled, false otherwise
     */
    boolean existsByStudentAndCourseAndIsActiveTrue(User student, Course course);
    
    /**
     * Find all enrollments by student
     * 
     * @param student the student to filter by
     * @return List of enrollments for the student
     */
    List<Enrollment> findByStudent(User student);
    
    /**
     * Find active enrollments by student
     * 
     * @param student the student to filter by
     * @return List of active enrollments for the student
     */
    List<Enrollment> findByStudentAndIsActiveTrue(User student);
    
    /**
     * Find all enrollments by course
     * 
     * @param course the course to filter by
     * @return List of enrollments for the course
     */
    List<Enrollment> findByCourse(Course course);
    
    /**
     * Find active enrollments by course
     * 
     * @param course the course to filter by
     * @return List of active enrollments for the course
     */
    List<Enrollment> findByCourseAndIsActiveTrue(Course course);
    
    /**
     * Count active enrollments for a course
     * 
     * @param course the course to count enrollments for
     * @return number of active enrollments
     */
    long countByCourseAndIsActiveTrue(Course course);
    
    /**
     * Find enrollments by student and active status
     * 
     * @param student the student to filter by
     * @param isActive the active status to filter by
     * @return List of enrollments matching the criteria
     */
    List<Enrollment> findByStudentAndIsActive(User student, Boolean isActive);
    
    /**
     * Find recent enrollments (last 30 days)
     * 
     * @return List of recent enrollments
     */
    @Query("SELECT e FROM Enrollment e WHERE e.enrolledAt >= CURRENT_DATE - 30")
    List<Enrollment> findRecentEnrollments();
    
    /**
     * Find enrollments by date range
     * 
     * @param startDate start date
     * @param endDate end date
     * @return List of enrollments within the date range
     */
    @Query("SELECT e FROM Enrollment e WHERE e.enrolledAt BETWEEN :startDate AND :endDate")
    List<Enrollment> findByEnrollmentDateBetween(@Param("startDate") java.time.LocalDateTime startDate, 
                                                 @Param("endDate") java.time.LocalDateTime endDate);
}