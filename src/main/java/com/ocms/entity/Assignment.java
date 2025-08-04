package com.ocms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Assignment entity representing assignments created by instructors for courses
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Entity
@Table(name = "assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Assignment title is required")
    @Size(min = 3, max = 100, message = "Assignment title must be between 3 and 100 characters")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Assignment description is required")
    @Size(min = 10, max = 1000, message = "Assignment description must be between 10 and 1000 characters")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    
    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;
    
    @Column(name = "max_score")
    private Integer maxScore;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AssignmentSubmission> submissions = new HashSet<>();
    
    // JPA Lifecycle methods
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Helper methods
    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(dueDate);
    }
    
    public boolean isDueSoon() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDayBefore = dueDate.minusDays(1);
        return now.isAfter(oneDayBefore) && now.isBefore(dueDate);
    }
}