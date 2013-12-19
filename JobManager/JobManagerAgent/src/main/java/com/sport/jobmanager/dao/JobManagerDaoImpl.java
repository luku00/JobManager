package com.sport.jobmanager.dao;

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
}
