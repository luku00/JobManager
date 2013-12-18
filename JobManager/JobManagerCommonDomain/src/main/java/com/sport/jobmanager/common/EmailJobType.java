package com.sport.jobmanager.common;

/**
 *
 * @author Lukas Kubicek 
 */
public enum EmailJobType {

    NEW_SUB_USER,
    RESET_PASSWORD;

    public static EmailJobType fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (EmailJobType emailJob : values()) {
            if (emailJob.name().equalsIgnoreCase(value)) {
                return emailJob;
            }
        }
        return null;
    }
}
