package com.sport.jobmanager.dao;

import com.sport.jobmanager.common.JobStatus;
import com.sport.jobmanager.common.domain.Job;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lukas Kubicek
 */
@Repository
@Transactional
@SuppressWarnings("unchecked")
public class JobManagerDaoImpl implements JobManagerDao {

    @Autowired
    private SessionFactory sessionFactory;

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Job> getJobs(JobStatus status) {
        Query query = null;
        if (JobStatus.INITIAL == status) {
            query = getCurrentSession().getNamedQuery(Job.JOBS_READY_TO_PICK_UP);
        }
        if (query == null) {
            return null;
        }
        List<Job> result = (List<Job>) query.list();
        return result;
    }

    @Override
    public List<Job> getJobsReadyForProcessing(String agentName) {
        Query query = getCurrentSession().getNamedQuery(Job.JOBS_READY_TO_PROCESS);
        query.setParameter("trueValue", true);
        query.setParameter("agentName", agentName);
        List<Job> result = (List<Job>) query.list();
        return result;
    }

    @Override
    public List<Job> getJobsToCleanUp(Timestamp cleanDate) {
        Query query = getCurrentSession().getNamedQuery(Job.JOBS_READY_TO_CLEAN);
        query.setParameter("cleanDate", cleanDate);
        List<Job> result = (List<Job>) query.list();
        return result;
    }

    @Override
    public void removeJob(Job job) {
        getCurrentSession().delete(job);
    }
}
