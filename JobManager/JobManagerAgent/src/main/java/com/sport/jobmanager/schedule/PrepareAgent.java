package com.sport.jobmanager.schedule;

import com.sport.jobmanager.common.JobStatus;
import com.sport.jobmanager.common.domain.Job;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This agent is for job preparation. Will pickup any job in INITIAL status and
 * set all required fields and pass it to specific agent
 *
 * @author Lukas Kubicek
 */
public class PrepareAgent extends Agent {

    static Logger LOGGER = LoggerFactory.getLogger("agent");

    private int jobAge;
    private int numberOfReprocess;

    @Override
    protected void agentSpecificLogic(String agentName, String agentType) {
        List<Job> newJobs = getJobManagerDao().getJobs(JobStatus.INITIAL);
        processJobs(newJobs, agentName);
    }

    private void processJobs(List<Job> jobs, String agentName) {
        Date currentTime = new Date();
        Date jobExpiredTime = new Date(currentTime.getTime() + TimeUnit.HOURS.toMillis(jobAge));
        for (Job job : jobs) {
            LOGGER.info(agentName + ", JobId :" + job.getJobId());
            job.setReprocess(true);
            job.setReprocessCount(numberOfReprocess);
            job.setJobExpiration(new Timestamp(jobExpiredTime.getTime()));
            job.setJobStatus(JobStatus.READY_TO_PROCESS);
            job.setAgentName(job.getJobType().getValue());
        }
    }

    public void setJobAge(int jobAge) {
        this.jobAge = jobAge;
    }

    public void setNumberOfReprocess(int numberOfReprocess) {
        this.numberOfReprocess = numberOfReprocess;
    }

    @Override
    protected void agentSpecificPostLogic(Job job, boolean success) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
