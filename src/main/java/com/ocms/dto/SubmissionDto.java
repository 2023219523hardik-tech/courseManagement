package com.ocms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for assignment submission requests
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDto {
    
    @NotNull(message = "Assignment ID is required")
    private Long assignmentId;
    
    private String content;
    
    private String fileUrl;
}