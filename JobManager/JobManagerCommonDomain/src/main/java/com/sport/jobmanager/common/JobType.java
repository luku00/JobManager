package com.sport.jobmanager.common;

/**
 *
 * @author Lukas Kubicek 
 */
public enum JobType {

    EMAIL_NEW_SUB_USER,
    EMAIL_RESET_PASSWORD;

    public static JobType fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (JobType job : values()) {
            if (job.name().equalsIgnoreCase(value)) {
                return job;
            }
        }
        return null;
    }
}
