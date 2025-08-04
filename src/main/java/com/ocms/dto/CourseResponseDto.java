package com.ocms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for course response data
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {
    
    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private Integer maxStudents;
    private Integer enrolledStudentCount;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private UserResponseDto instructor;
    private Boolean isFull;
    private Boolean canEnroll;
}