package com.ocms.service;

import com.ocms.dto.LoginDto;
import com.ocms.dto.UserResponseDto;
import com.ocms.entity.User;
import com.ocms.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service class for authentication-related operations
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Service
public class AuthenticationService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserService userService;
    
    /**
     * Authenticate user and generate JWT token
     * 
     * @param loginDto the login credentials
     * @return authentication response with token and user info
     */
    public AuthenticationResponse authenticate(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);
            
            User user = userService.getUserEntityByEmail(loginDto.getEmail());
            UserResponseDto userResponse = userService.getUserByEmail(loginDto.getEmail());
            
            return new AuthenticationResponse(token, userResponse);
            
        } catch (Exception e) {
            throw new RuntimeException("Invalid email or password");
        }
    }
    
    /**
     * Get current user from JWT token
     * 
     * @param token the JWT token
     * @return the current user
     */
    public UserResponseDto getCurrentUser(String token) {
        String email = jwtUtil.extractUsername(token);
        return userService.getUserByEmail(email);
    }
    
    /**
     * Validate JWT token
     * 
     * @param token the JWT token
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            String email = jwtUtil.extractUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(email);
            return jwtUtil.validateToken(token, userDetails);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Inner class for authentication response
     */
    public static class AuthenticationResponse {
        private String token;
        private UserResponseDto user;
        
        public AuthenticationResponse(String token, UserResponseDto user) {
            this.token = token;
            this.user = user;
        }
        
        // Getters and setters
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        
        public UserResponseDto getUser() { return user; }
        public void setUser(UserResponseDto user) { this.user = user; }
    }
}