package com.sport.jobmanager.common;

/**
 *
 * @author Lukas Kubicek 
 */
public enum JobStatus {

    /**
     * initial state ... ready to pick up
     */
    INITIAL,
    /**
     * has been verified by agent and is ready to process
     */
    READY_TO_PROCESS,
    /**
     * job is processing
     */
    PROCESSING,
    /**
     * Job has been successfully completed
     */
    COMPLETED,
    /**
     * Job failed
     */
    FAILED;

    public static JobStatus fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        for (JobStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }
}
