package com.ocms.service;

import com.ocms.dto.CourseDto;
import com.ocms.dto.CourseResponseDto;
import com.ocms.dto.UserResponseDto;
import com.ocms.entity.Course;
import com.ocms.entity.User;
import com.ocms.enums.UserRole;
import com.ocms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for course-related operations
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserService userService;
    
    /**
     * Create a new course
     * 
     * @param courseDto the course data
     * @param instructorId the instructor ID
     * @return the created course response
     */
    public CourseResponseDto createCourse(CourseDto courseDto, Long instructorId) {
        User instructor = userService.getUserEntityById(instructorId);
        
        if (instructor.getRole() != UserRole.INSTRUCTOR) {
            throw new RuntimeException("Only instructors can create courses");
        }
        
        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setDuration(courseDto.getDuration());
        course.setMaxStudents(courseDto.getMaxStudents());
        course.setInstructor(instructor);
        course.setIsActive(true);
        
        Course savedCourse = courseRepository.save(course);
        return convertToResponseDto(savedCourse);
    }
    
    /**
     * Get course by ID
     * 
     * @param id the course ID
     * @return the course response
     */
    public CourseResponseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        return convertToResponseDto(course);
    }
    
    /**
     * Get all active courses
     * 
     * @return list of all active courses
     */
    public List<CourseResponseDto> getAllActiveCourses() {
        return courseRepository.findByIsActiveTrue().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get courses by instructor
     * 
     * @param instructorId the instructor ID
     * @return list of courses by the instructor
     */
    public List<CourseResponseDto> getCoursesByInstructor(Long instructorId) {
        User instructor = userService.getUserEntityById(instructorId);
        return courseRepository.findByInstructorAndIsActiveTrue(instructor).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Search courses by title
     * 
     * @param title the title to search for
     * @return list of courses matching the title
     */
    public List<CourseResponseDto> searchCoursesByTitle(String title) {
        return courseRepository.findByTitleContainingIgnoreCaseAndIsActiveTrue(title).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Search courses by description
     * 
     * @param description the description to search for
     * @return list of courses matching the description
     */
    public List<CourseResponseDto> searchCoursesByDescription(String description) {
        return courseRepository.findByDescriptionContainingIgnoreCaseAndIsActiveTrue(description).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get available courses (not full)
     * 
     * @return list of available courses
     */
    public List<CourseResponseDto> getAvailableCourses() {
        return courseRepository.findAvailableCourses().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get courses by duration range
     * 
     * @param minDuration minimum duration in hours
     * @param maxDuration maximum duration in hours
     * @return list of courses within the duration range
     */
    public List<CourseResponseDto> getCoursesByDurationRange(Integer minDuration, Integer maxDuration) {
        return courseRepository.findByDurationBetweenAndIsActiveTrue(minDuration, maxDuration).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Get courses with high enrollment
     * 
     * @param minEnrollment minimum number of enrolled students
     * @return list of courses with high enrollment
     */
    public List<CourseResponseDto> getCoursesWithHighEnrollment(int minEnrollment) {
        return courseRepository.findCoursesWithHighEnrollment(minEnrollment).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Update course
     * 
     * @param id the course ID
     * @param courseDto the updated course data
     * @param instructorId the instructor ID (for authorization)
     * @return the updated course response
     */
    public CourseResponseDto updateCourse(Long id, CourseDto courseDto, Long instructorId) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        
        // Check if the user is the instructor of this course
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new RuntimeException("Only the course instructor can update this course");
        }
        
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setDuration(courseDto.getDuration());
        course.setMaxStudents(courseDto.getMaxStudents());
        
        Course updatedCourse = courseRepository.save(course);
        return convertToResponseDto(updatedCourse);
    }
    
    /**
     * Deactivate course
     * 
     * @param id the course ID
     * @param instructorId the instructor ID (for authorization)
     */
    public void deactivateCourse(Long id, Long instructorId) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        
        // Check if the user is the instructor of this course
        if (!course.getInstructor().getId().equals(instructorId)) {
            throw new RuntimeException("Only the course instructor can deactivate this course");
        }
        
        course.setIsActive(false);
        courseRepository.save(course);
    }
    
    /**
     * Get course entity by ID (for internal use)
     * 
     * @param id the course ID
     * @return the course entity
     */
    public Course getCourseEntityById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }
    
    /**
     * Convert course entity to response DTO
     * 
     * @param course the course entity
     * @return the course response DTO
     */
    private CourseResponseDto convertToResponseDto(Course course) {
        CourseResponseDto dto = new CourseResponseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setDuration(course.getDuration());
        dto.setMaxStudents(course.getMaxStudents());
        dto.setEnrolledStudentCount(course.getEnrolledStudentCount());
        dto.setIsActive(course.getIsActive());
        dto.setCreatedAt(course.getCreatedAt());
        dto.setIsFull(course.isFull());
        dto.setCanEnroll(course.canEnroll());
        
        // Convert instructor to DTO
        UserResponseDto instructorDto = new UserResponseDto();
        instructorDto.setId(course.getInstructor().getId());
        instructorDto.setFirstName(course.getInstructor().getFirstName());
        instructorDto.setLastName(course.getInstructor().getLastName());
        instructorDto.setEmail(course.getInstructor().getEmail());
        instructorDto.setRole(course.getInstructor().getRole());
        instructorDto.setFullName(course.getInstructor().getFullName());
        dto.setInstructor(instructorDto);
        
        return dto;
    }
}