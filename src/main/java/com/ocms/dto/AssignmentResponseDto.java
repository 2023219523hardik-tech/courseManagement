package com.ocms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for assignment response data
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponseDto {
    
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Integer maxScore;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private Long courseId;
    private String courseTitle;
    private Boolean isOverdue;
    private Boolean isDueSoon;
}