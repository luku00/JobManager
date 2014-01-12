package com.sport.jobmanager.service;

import com.sport.jobmanager.common.domain.Job;

/**
 * @author Lukas Kubicek
 */
public interface EmailService {

    /**
     * This will create and send email to new created sub user
     * @param job
     */
    void sendEmailForNewSubUser(Job job);

    /**
     * This will create and send email for password reset
     * @param job
     * @throws java.lang.Exception
     */
    void sendEmailForPasswordReset(Job job) throws Exception;
}
