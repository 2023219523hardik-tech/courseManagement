package com.ocms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for assignment creation and update requests
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDto {
    
    @NotBlank(message = "Assignment title is required")
    @Size(min = 3, max = 100, message = "Assignment title must be between 3 and 100 characters")
    private String title;
    
    @NotBlank(message = "Assignment description is required")
    @Size(min = 10, max = 1000, message = "Assignment description must be between 10 and 1000 characters")
    private String description;
    
    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;
    
    @Positive(message = "Max score must be positive")
    private Integer maxScore;
}