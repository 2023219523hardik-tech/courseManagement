package com.ocms.config;

import com.ocms.entity.User;
import com.ocms.enums.UserRole;
import com.ocms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Data initializer to populate the database with sample data
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize sample users if the database is empty
        if (userRepository.count() == 0) {
            initializeSampleUsers();
        }
    }
    
    /**
     * Initialize sample users for testing
     */
    private void initializeSampleUsers() {
        // Create sample instructor
        User instructor = new User();
        instructor.setFirstName("John");
        instructor.setLastName("Smith");
        instructor.setEmail("john.smith@ocms.com");
        instructor.setPassword(passwordEncoder.encode("password123"));
        instructor.setRole(UserRole.INSTRUCTOR);
        instructor.setPhoneNumber("+1234567890");
        instructor.setIsActive(true);
        userRepository.save(instructor);
        
        // Create sample students
        User student1 = new User();
        student1.setFirstName("Alice");
        student1.setLastName("Johnson");
        student1.setEmail("alice.johnson@student.com");
        student1.setPassword(passwordEncoder.encode("password123"));
        student1.setRole(UserRole.STUDENT);
        student1.setPhoneNumber("+1234567891");
        student1.setIsActive(true);
        userRepository.save(student1);
        
        User student2 = new User();
        student2.setFirstName("Bob");
        student2.setLastName("Williams");
        student2.setEmail("bob.williams@student.com");
        student2.setPassword(passwordEncoder.encode("password123"));
        student2.setRole(UserRole.STUDENT);
        student2.setPhoneNumber("+1234567892");
        student2.setIsActive(true);
        userRepository.save(student2);
        
        User student3 = new User();
        student3.setFirstName("Carol");
        student3.setLastName("Brown");
        student3.setEmail("carol.brown@student.com");
        student3.setPassword(passwordEncoder.encode("password123"));
        student3.setRole(UserRole.STUDENT);
        student3.setPhoneNumber("+1234567893");
        student3.setIsActive(true);
        userRepository.save(student3);
        
        System.out.println("✅ Sample users initialized successfully!");
        System.out.println("📧 Sample login credentials:");
        System.out.println("   Instructor: john.smith@ocms.com / password123");
        System.out.println("   Student 1: alice.johnson@student.com / password123");
        System.out.println("   Student 2: bob.williams@student.com / password123");
        System.out.println("   Student 3: carol.brown@student.com / password123");
    }
}