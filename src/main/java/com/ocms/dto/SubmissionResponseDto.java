package com.ocms.dto;

import com.ocms.enums.AssignmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for submission response data
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionResponseDto {
    
    private Long id;
    private String content;
    private String fileUrl;
    private LocalDateTime submittedAt;
    private AssignmentStatus status;
    private Integer score;
    private String feedback;
    private LocalDateTime gradedAt;
    private Long gradedBy;
    private Long assignmentId;
    private String assignmentTitle;
    private Long studentId;
    private String studentName;
    private Boolean isLate;
    private Boolean isGraded;
    private Double percentage;
}