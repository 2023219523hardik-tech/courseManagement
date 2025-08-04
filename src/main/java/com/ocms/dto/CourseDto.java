package com.ocms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for course creation and update requests
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    
    @NotBlank(message = "Course title is required")
    @Size(min = 3, max = 100, message = "Course title must be between 3 and 100 characters")
    private String title;
    
    @NotBlank(message = "Course description is required")
    @Size(min = 10, max = 1000, message = "Course description must be between 10 and 1000 characters")
    private String description;
    
    @NotNull(message = "Course duration is required")
    @Positive(message = "Duration must be positive")
    private Integer duration;
    
    private Integer maxStudents;
}