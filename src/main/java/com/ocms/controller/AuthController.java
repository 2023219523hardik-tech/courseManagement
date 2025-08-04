package com.ocms.controller;

import com.ocms.dto.LoginDto;
import com.ocms.dto.UserRegistrationDto;
import com.ocms.dto.UserResponseDto;
import com.ocms.service.AuthenticationService;
import com.ocms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for authentication-related endpoints
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    private UserService userService;
    
    /**
     * Register a new user
     * 
     * @param registrationDto the registration data
     * @return the created user response
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegistrationDto registrationDto) {
        try {
            UserResponseDto user = userService.registerUser(registrationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Login user and get JWT token
     * 
     * @param loginDto the login credentials
     * @return authentication response with token and user info
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationService.AuthenticationResponse> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            AuthenticationService.AuthenticationResponse response = authenticationService.authenticate(loginDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    /**
     * Get current user info
     * 
     * @param token the JWT token (from Authorization header)
     * @return the current user info
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.replace("Bearer ", "");
            UserResponseDto user = authenticationService.getCurrentUser(jwtToken);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}