package com.sport.jobmanager.schedule;

import com.sport.jobmanager.common.JobStatus;
import com.sport.jobmanager.common.JobType;
import com.sport.jobmanager.common.domain.Job;
import com.sport.jobmanager.service.EmailService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukas Kubicek
 */
public class EmailAgent extends Agent {

    static Logger LOGGER = LoggerFactory.getLogger("agent");

    private EmailService emailService;

    @Override
    protected void agentSpecificLogic(String agentName, String agentType) throws Exception {
        List<Job> jobsReadyToProcess = getJobManagerDao().getJobsReadyForProcessing(agentName);
        if (jobsReadyToProcess != null && !jobsReadyToProcess.isEmpty()) {
            try {
                processJob(jobsReadyToProcess.get(0), agentName);
                agentSpecificPostLogic(jobsReadyToProcess.get(0), true);
            } catch (Exception e) {
                agentSpecificPostLogic(jobsReadyToProcess.get(0), false);
                throw e;
            }
        }
    }

    private void processJob(Job job, String agentName) throws Exception {
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

    @Override
    protected void agentSpecificPostLogic(Job job, boolean success) {
        if (success) {
            job.setJobStatus(JobStatus.COMPLETED);
            job.setReprocess(false);
        } else {
            job.setJobStatus(JobStatus.FAILED);
            job.setReprocessCount(job.getReprocessCount() - 1);
            if (job.getReprocessCount() == 0) {
                job.setReprocess(false);
            }
        }
    }

}
