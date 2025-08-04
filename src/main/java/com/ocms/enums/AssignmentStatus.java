package com.ocms.enums;

/**
 * Enum representing assignment submission status
 * 
 * @author OCMS Team
 * @version 1.0.0
 */
public enum AssignmentStatus {
    PENDING("Pending"),
    SUBMITTED("Submitted"),
    GRADED("Graded"),
    LATE("Late");
    
    private final String displayName;
    
    AssignmentStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}