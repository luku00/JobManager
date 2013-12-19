package com.sport.jobmanager.schedule;

import com.sport.jobmanager.common.JobStatus;
import com.sport.jobmanager.common.domain.Job;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukas Kubicek
 */
public class PrepareAgent extends Agent {

    static Logger LOGGER = LoggerFactory.getLogger("agent");

    @Override
    protected void agentSpecificLogic(String agentName, String agentType) {
        List<Job> newJobs = getJobManagerDao().getJobs(JobStatus.INITIAL);
        processJob(newJobs.get(0), agentName);
    }

    private void processJob(Job job, String agentName) {
        LOGGER.info(agentName + ", JobId :" + job.getJobId());
    }
}
