/***************************************************************************************************
 * Copyright 2013 TeliaSonera. All rights reserved.
 **************************************************************************************************/
package com.sport.jobmanager.schedule;

import com.sport.jobmanager.common.JobType;
import com.sport.jobmanager.common.domain.Job;
import com.sport.jobmanager.service.EmailService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukas Kubicek <lukas.kubicek@netcom-gsm.com>
 */
public class EmailAgent extends Agent {

    static Logger LOGGER = LoggerFactory.getLogger("agent");

    private EmailService emailService;

    @Override
    protected void agentSpecificLogic(String agentName, String agentType) {
        List<Job> jobsReadyToProcess = getJobManagerDao().getJobsReadyForProcessing(agentName);
        if (jobsReadyToProcess != null && !jobsReadyToProcess.isEmpty()) {
            processJob(jobsReadyToProcess.get(0), agentName);
        }
    }

    private void processJob(Job job, String agentName) {
        LOGGER.info(agentName + ", JobId :" + job.getJobId());
        if (JobType.EMAIL_NEW_SUB_USER == job.getJobType()) {
            emailService.sendEmailForNewSubUser(job);
        } else if (JobType.EMAIL_RESET_PASSWORD == job.getJobType()) {
            emailService.sendEmailForPasswordReset(job);
        }
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

}
