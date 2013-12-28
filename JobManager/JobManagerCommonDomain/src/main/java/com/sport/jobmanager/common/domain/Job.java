package com.sport.jobmanager.common.domain;

import com.sport.jobmanager.common.JobStatus;
import com.sport.jobmanager.common.JobType;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import static javax.persistence.EnumType.STRING;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

/**
 *
 * @author Lukas Kubicek 
 */
@NamedQueries({
    @NamedQuery(name = "Job.findJobReadyToPickUp", query = "select j from Job j where jobStatus = 'INITIAL' ORDER BY jobId DESC"),
    @NamedQuery(name = "Job.findJobReadyToProcess", query = "select j from Job j where reprocess = :trueValue AND agentName =:agentName"),
    @NamedQuery(name = "Job.findJobsreadyToClean", query = "select j from Job j where jobStatus = 'COMPLETED' AND jobExpiration < :cleanDate")
})
@Entity
@Table(name = "JOBS")
public class Job implements Serializable {

    public static final String JOBS_READY_TO_PICK_UP = "Job.findJobReadyToPickUp";
    public static final String JOBS_READY_TO_PROCESS = "Job.findJobReadyToProcess";
    public static final String JOBS_READY_TO_CLEAN = "Job.findJobsreadyToClean";

    @Id
    @Column(name = "JOB_ID")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer jobId;

    @Column(name = "USER_FIRST_NAME")
    private String userFirstName;

    @Column(name = "USER_LAST_NAME")
    private String userLastName;

    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Enumerated(STRING)
    @Column(name = "JOB_TYPE")
    private JobType jobType;

    @Enumerated(STRING)
    @Column(name = "JOB_STATUS")
    private JobStatus jobStatus;

    @Column(name = "REPROCESS")
    private boolean reprocess;

    @Column(name = "REPROCESS_COUNT")
    private int reprocessCount;

    @Column(name = "JOB_EXPIRATION")
    private Timestamp jobExpiration;

    @Column(name = "AGENT_NAME")
    private String agentName;

    // specific identifier
    @Column(name = "JOB_IDENTIFIER")
    private String jobIdentifier;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public boolean isReprocess() {
        return reprocess;
    }

    public void setReprocess(boolean reprocess) {
        this.reprocess = reprocess;
    }

    public int getReprocessCount() {
        return reprocessCount;
    }

    public void setReprocessCount(int reprocessCount) {
        this.reprocessCount = reprocessCount;
    }

    public Date getJobExpiration() {
        return jobExpiration;
    }

    public void setJobExpiration(Timestamp jobExpiration) {
        this.jobExpiration = jobExpiration;
    }

    public DateTime getJobExpirationDate() {
        return new DateTime(jobExpiration);
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getJobIdentifier() {
        return jobIdentifier;
    }

    public void setJobIdentifier(String jobIdentifier) {
        this.jobIdentifier = jobIdentifier;
    }

    @Override
    public String toString() {
        return "Job{" + "jobId=" + jobId + ", userFirstName=" + userFirstName
                + ", userLastName=" + userLastName + ", userEmail=" + userEmail
                + ", jobType=" + jobType + ", jobStatus=" + jobStatus + ", reprocess="
                + reprocess + ", reprocessCount=" + reprocessCount + ", jobExpiration="
                + jobExpiration + ", agentName=" + agentName + ", jobIdentifier=" + jobIdentifier + '}';
    }

}
