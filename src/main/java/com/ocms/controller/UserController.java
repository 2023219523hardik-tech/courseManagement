package com.ocms.controller;

import com.ocms.dto.UserRegistrationDto;
import com.ocms.dto.UserResponseDto;
import com.ocms.enums.UserRole;
import com.ocms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for user management endpoints
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * Get all users
     * 
     * @return list of all users
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        try {
            List<UserResponseDto> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get user by ID
     * 
     * @param id the user ID
     * @return the user response
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        try {
            UserResponseDto user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get all active users
     * 
     * @return list of active users
     */
    @GetMapping("/active")
    public ResponseEntity<List<UserResponseDto>> getActiveUsers() {
        try {
            List<UserResponseDto> users = userService.getActiveUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get users by role
     * 
     * @param role the role to filter by
     * @return list of users with the specified role
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponseDto>> getUsersByRole(@PathVariable UserRole role) {
        try {
            List<UserResponseDto> users = userService.getUsersByRole(role);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get all instructors
     * 
     * @return list of all instructors
     */
    @GetMapping("/instructors")
    public ResponseEntity<List<UserResponseDto>> getAllInstructors() {
        try {
            List<UserResponseDto> instructors = userService.getAllInstructors();
            return ResponseEntity.ok(instructors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Get all students
     * 
     * @return list of all students
     */
    @GetMapping("/students")
    public ResponseEntity<List<UserResponseDto>> getAllStudents() {
        try {
            List<UserResponseDto> students = userService.getAllStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Search users by name
     * 
     * @param name the name to search for
     * @return list of users matching the name
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDto>> searchUsersByName(@RequestParam String name) {
        try {
            List<UserResponseDto> users = userService.searchUsersByName(name);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Update user
     * 
     * @param id the user ID
     * @param userDto the updated user data
     * @return the updated user response
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, 
                                                   @Valid @RequestBody UserRegistrationDto userDto) {
        try {
            UserResponseDto updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Deactivate user
     * 
     * @param id the user ID
     * @return success response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        try {
            userService.deactivateUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Activate user
     * 
     * @param id the user ID
     * @return success response
     */
    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateUser(@PathVariable Long id) {
        try {
            userService.activateUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}