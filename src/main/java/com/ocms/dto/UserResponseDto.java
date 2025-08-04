package com.ocms.dto;

import com.ocms.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for user response data
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private String phoneNumber;
    private String fullName;
    private LocalDateTime createdAt;
    private Boolean isActive;
}