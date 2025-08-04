package com.ocms.entity;

import com.ocms.enums.AssignmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * AssignmentSubmission entity representing student submissions for assignments
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Entity
@Table(name = "assignment_submissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentSubmission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "file_url")
    private String fileUrl;
    
    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignmentStatus status = AssignmentStatus.PENDING;
    
    @Column(name = "score")
    private Integer score;
    
    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;
    
    @Column(name = "graded_at")
    private LocalDateTime gradedAt;
    
    @Column(name = "graded_by")
    private Long gradedBy;
    
    // JPA Lifecycle methods
    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
        if (assignment != null && assignment.isOverdue()) {
            status = AssignmentStatus.LATE;
        } else {
            status = AssignmentStatus.SUBMITTED;
        }
    }
    
    // Helper methods
    public boolean isLate() {
        return assignment != null && submittedAt.isAfter(assignment.getDueDate());
    }
    
    public boolean isGraded() {
        return status == AssignmentStatus.GRADED;
    }
    
    public void grade(Integer score, String feedback, Long gradedBy) {
        this.score = score;
        this.feedback = feedback;
        this.gradedBy = gradedBy;
        this.status = AssignmentStatus.GRADED;
        this.gradedAt = LocalDateTime.now();
    }
    
    public double getPercentage() {
        if (score == null || assignment.getMaxScore() == null || assignment.getMaxScore() == 0) {
            return 0.0;
        }
        return (double) score / assignment.getMaxScore() * 100;
    }
}