package com.ocms.enums;

/**
 * Enum representing user roles in the OCMS system
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
public enum UserRole {
    STUDENT("Student"),
    INSTRUCTOR("Instructor");
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}