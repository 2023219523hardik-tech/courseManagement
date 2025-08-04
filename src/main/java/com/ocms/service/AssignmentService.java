package com.ocms.service;

import com.ocms.dto.AssignmentDto;
import com.ocms.dto.AssignmentResponseDto;
import com.ocms.entity.Assignment;
import com.ocms.entity.Course;
import com.ocms.entity.User;
import com.ocms.enums.UserRole;
import com.ocms.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for assignment-related operations
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Service
public class AssignmentService {
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CourseService courseService;
    
    /**
     * Create a new assignment
     * 
     * @param assignmentDto the assignment data
     * @param courseId the course ID
     * @param instructorId the instructor ID (for authorization)
     * @return the created assignment response
     */
    public AssignmentResponseDto createAssignment(AssignmentDto assignmentDto, Long courseId, Long instructorId) {
        Course course = courseService.getCourseEntityById(courseId);
        User instructor = userService.getUserEntityById(instructorId);
        
        // Check if the user is the instructor of this course
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new RuntimeException("Only the course instructor can create assignments for this course");
        }
        
        // Check if user is an instructor
        if (instructor.getRole() != UserRole.INSTRUCTOR) {
            throw new RuntimeException("Only instructors can create assignments");
        }
        
        Assignment assignment = new Assignment();
        assignment.setTitle(assignmentDto.getTitle());
        assignment.setDescription(assignmentDto.getDescription());
        assignment.setDueDate(assignmentDto.getDueDate());
        assignment.setMaxScore(assignmentDto.getMaxScore());
        assignment.setCourse(course);
        assignment.setIsActive(true);
        
        Assignment savedAssignment = assignmentRepository.save(assignment);
        return convertToResponseDto(savedAssignment);
    }
    
    /**
     * Get assignment by ID
     * 
     * @param id the assignment ID
     * @return the assignment response
     */
    public AssignmentResponseDto getAssignmentById(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found with id: " + id));
        return convertToResponseDto(assignment);
    }
    
    /**
     * Get all active assignments
     * 
     * @return list of all active assignments
     */
    public List<AssignmentResponseDto> getAllActiveAssignments() {
        return assignmentRepository.findByIsActiveTrue().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get assignments by course
     * 
     * @param courseId the course ID
     * @return list of assignments for the course
     */
    public List<AssignmentResponseDto> getAssignmentsByCourse(Long courseId) {
        Course course = courseService.getCourseEntityById(courseId);
        return assignmentRepository.findByCourseAndIsActiveTrue(course).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Search assignments by title
     * 
     * @param title the title to search for
     * @return list of assignments matching the title
     */
    public List<AssignmentResponseDto> searchAssignmentsByTitle(String title) {
        return assignmentRepository.findByTitleContainingIgnoreCaseAndIsActiveTrue(title).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get assignments due soon (within next 24 hours)
     * 
     * @return list of assignments due soon
     */
    public List<AssignmentResponseDto> getAssignmentsDueSoon() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);
        return assignmentRepository.findAssignmentsDueSoon(now, tomorrow).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get overdue assignments
     * 
     * @return list of overdue assignments
     */
    public List<AssignmentResponseDto> getOverdueAssignments() {
        return assignmentRepository.findOverdueAssignments(LocalDateTime.now()).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get assignments by due date range
     * 
     * @param startDate start date
     * @param endDate end date
     * @return list of assignments within the due date range
     */
    public List<AssignmentResponseDto> getAssignmentsByDueDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return assignmentRepository.findByDueDateBetweenAndIsActiveTrue(startDate, endDate).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get assignments with high max score
     * 
     * @param minScore minimum max score
     * @return list of assignments with high max score
     */
    public List<AssignmentResponseDto> getAssignmentsWithHighScore(Integer minScore) {
        return assignmentRepository.findByMaxScoreGreaterThanAndIsActiveTrue(minScore).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Update assignment
     * 
     * @param id the assignment ID
     * @param assignmentDto the updated assignment data
     * @param instructorId the instructor ID (for authorization)
     * @return the updated assignment response
     */
    public AssignmentResponseDto updateAssignment(Long id, AssignmentDto assignmentDto, Long instructorId) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found with id: " + id));
        
        // Check if the user is the instructor of this course
        if (!assignment.getCourse().getInstructor().getId().equals(instructorId)) {
            throw new RuntimeException("Only the course instructor can update this assignment");
        }
        
        assignment.setTitle(assignmentDto.getTitle());
        assignment.setDescription(assignmentDto.getDescription());
        assignment.setDueDate(assignmentDto.getDueDate());
        assignment.setMaxScore(assignmentDto.getMaxScore());
        
        Assignment updatedAssignment = assignmentRepository.save(assignment);
        return convertToResponseDto(updatedAssignment);
    }
    
    /**
     * Deactivate assignment
     * 
     * @param id the assignment ID
     * @param instructorId the instructor ID (for authorization)
     */
    public void deactivateAssignment(Long id, Long instructorId) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found with id: " + id));
        
        // Check if the user is the instructor of this course
        if (!assignment.getCourse().getInstructor().getId().equals(instructorId)) {
            throw new RuntimeException("Only the course instructor can deactivate this assignment");
        }
        
        assignment.setIsActive(false);
        assignmentRepository.save(assignment);
    }
    
    /**
     * Get assignment entity by ID (for internal use)
     * 
     * @param id the assignment ID
     * @return the assignment entity
     */
    public Assignment getAssignmentEntityById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found with id: " + id));
    }
    
    /**
     * Convert assignment entity to response DTO
     * 
     * @param assignment the assignment entity
     * @return the assignment response DTO
     */
    private AssignmentResponseDto convertToResponseDto(Assignment assignment) {
        AssignmentResponseDto dto = new AssignmentResponseDto();
        dto.setId(assignment.getId());
        dto.setTitle(assignment.getTitle());
        dto.setDescription(assignment.getDescription());
        dto.setDueDate(assignment.getDueDate());
        dto.setMaxScore(assignment.getMaxScore());
        dto.setIsActive(assignment.getIsActive());
        dto.setCreatedAt(assignment.getCreatedAt());
        dto.setCourseId(assignment.getCourse().getId());
        dto.setCourseTitle(assignment.getCourse().getTitle());
        dto.setIsOverdue(assignment.isOverdue());
        dto.setIsDueSoon(assignment.isDueSoon());
        
        return dto;
    }
}