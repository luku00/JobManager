package com.sport.jobmanager.common;

/**
 * This define type of the job and also configure which agent should process it
 *
 * @author Lukas Kubicek 
 */
public enum JobType {

    /**
     * Email send to new sub user process by email agent
     */
    EMAIL_NEW_SUB_USER("EmailAgent"),
    /**
     * Email new password reset link process by email agent
     */
    EMAIL_RESET_PASSWORD("EmailAgent"),
    /**
     * Email notification that new plan has been created
     */
    EMAIL_NEW_PLAN("EmailAgent");

    private final String value;

    private JobType(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }

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
