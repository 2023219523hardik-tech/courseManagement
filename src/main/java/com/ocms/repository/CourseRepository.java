package com.ocms.repository;

import com.ocms.entity.Course;
import com.ocms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Course entity operations
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    /**
     * Find all active courses
     * 
     * @return List of active courses
     */
    List<Course> findByIsActiveTrue();
    
    /**
     * Find courses by instructor
     * 
     * @param instructor the instructor to filter by
     * @return List of courses taught by the instructor
     */
    List<Course> findByInstructor(User instructor);
    
    /**
     * Find active courses by instructor
     * 
     * @param instructor the instructor to filter by
     * @return List of active courses taught by the instructor
     */
    List<Course> findByInstructorAndIsActiveTrue(User instructor);
    
    /**
     * Find courses by title containing the search term
     * 
     * @param title the title to search for
     * @return List of courses with matching title
     */
    List<Course> findByTitleContainingIgnoreCaseAndIsActiveTrue(String title);
    
    /**
     * Find courses by description containing the search term
     * 
     * @param description the description to search for
     * @return List of courses with matching description
     */
    List<Course> findByDescriptionContainingIgnoreCaseAndIsActiveTrue(String description);
    
    /**
     * Find courses with available spots (not full)
     * 
     * @return List of courses that can accept more students
     */
    @Query("SELECT c FROM Course c WHERE c.isActive = true AND " +
           "(c.maxStudents IS NULL OR c.maxStudents > (SELECT COUNT(e) FROM Enrollment e WHERE e.course = c AND e.isActive = true))")
    List<Course> findAvailableCourses();
    
    /**
     * Find courses by duration range
     * 
     * @param minDuration minimum duration in hours
     * @param maxDuration maximum duration in hours
     * @return List of courses within the duration range
     */
    List<Course> findByDurationBetweenAndIsActiveTrue(Integer minDuration, Integer maxDuration);
    
    /**
     * Find courses with high enrollment (more than specified count)
     * 
     * @param minEnrollment minimum number of enrolled students
     * @return List of courses with high enrollment
     */
    @Query("SELECT c FROM Course c WHERE c.isActive = true AND " +
           "(SELECT COUNT(e) FROM Enrollment e WHERE e.course = c AND e.isActive = true) >= :minEnrollment")
    List<Course> findCoursesWithHighEnrollment(@Param("minEnrollment") int minEnrollment);
}