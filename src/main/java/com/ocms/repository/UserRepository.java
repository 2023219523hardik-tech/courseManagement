package com.ocms.repository;

import com.ocms.entity.User;
import com.ocms.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity operations
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by email
     * 
     * @param email the email to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if user exists by email
     * 
     * @param email the email to check
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Find all users by role
     * 
     * @param role the role to filter by
     * @return List of users with the specified role
     */
    List<User> findByRole(UserRole role);
    
    /**
     * Find all active users
     * 
     * @return List of active users
     */
    List<User> findByIsActiveTrue();
    
    /**
     * Find all users by role and active status
     * 
     * @param role the role to filter by
     * @param isActive the active status to filter by
     * @return List of users matching the criteria
     */
    List<User> findByRoleAndIsActive(UserRole role, Boolean isActive);
    
    /**
     * Find users by name (first name or last name contains the search term)
     * 
     * @param name the name to search for
     * @return List of users matching the name criteria
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Find all instructors
     * 
     * @return List of all instructors
     */
    @Query("SELECT u FROM User u WHERE u.role = 'INSTRUCTOR' AND u.isActive = true")
    List<User> findAllInstructors();
    
    /**
     * Find all students
     * 
     * @return List of all students
     */
    @Query("SELECT u FROM User u WHERE u.role = 'STUDENT' AND u.isActive = true")
    List<User> findAllStudents();
}