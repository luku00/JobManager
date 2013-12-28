package com.sport.jobmanager.schedule;

import com.sport.jobmanager.common.domain.Job;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author luku00
 */
public class CleanUpAgent extends Agent {

    static Logger LOGGER = LoggerFactory.getLogger("agent");
    static Logger LOGGER_CLEANED = LoggerFactory.getLogger("cleanedJobs");

    private int jobCleanDate;

    @Override
    protected void agentSpecificLogic(String agentName, String agentType) throws Exception {
        Date currentTime = new Date();
        Date cleanDate = new Date(currentTime.getTime() - TimeUnit.DAYS.toMillis(jobCleanDate));
        List<Job> jobsReadyToClean = getJobManagerDao().getJobsToCleanUp(new Timestamp(cleanDate.getTime()));

        if (jobsReadyToClean != null && !jobsReadyToClean.isEmpty()) {
            for (Job job : jobsReadyToClean) {
                LOGGER.info(agentName + ", JobId :" + job.getJobId());
                logJob(job);
                getJobManagerDao().removeJob(job);
            }
        }
    }

    private void logJob(Job job) {
        LOGGER_CLEANED.info(job.toString());
    }

    @Override
    protected void agentSpecificPostLogic(Job job, boolean success) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setJobCleanDate(int jobCleanDate) {
        this.jobCleanDate = jobCleanDate;
    }

}
