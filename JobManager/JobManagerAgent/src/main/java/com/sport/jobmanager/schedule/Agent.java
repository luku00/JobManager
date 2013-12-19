package com.sport.jobmanager.schedule;

import com.sport.jobmanager.dao.JobManagerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lukas Kubicek
 */
public abstract class Agent {

    static Logger LOGGER = LoggerFactory.getLogger("error");
    protected String agentName;
    private String agentType;
    private Integer millisToWait;
    private boolean running;

    private JobManagerDao jobManagerDao;

    @Transactional
    public void execute() {
        try {
            if (startAgentIfReady()) {
                agentSpecificLogic(agentName, agentType);
            }
        } catch (Exception e) {
            LOGGER.error(agentName, e);
        } finally {
            stopAgent();
        }
    }

    private synchronized void stopAgent() {
        running = false;
    }

    private synchronized boolean startAgentIfReady() throws InterruptedException {
        if (running) {
            return false;
        } else {
            running = true;
            if (millisToWait != null) {
                Thread.sleep(millisToWait);
            }
            return true;
        }
    }

    protected abstract void agentSpecificLogic(String agentName, String agentType);

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public void setMillisToWait(Integer millisToWait) {
        this.millisToWait = millisToWait;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public JobManagerDao getJobManagerDao() {
        return jobManagerDao;
    }

    public void setJobManagerDao(JobManagerDao jobManagerDao) {
        this.jobManagerDao = jobManagerDao;
    }

}
