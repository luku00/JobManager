package com.sport.jobmanager.serviceimpl;

import com.sport.jobmanager.common.domain.Job;
import com.sport.jobmanager.service.EmailService;

/**
 *
 * @author Lukas Kubicek
 */
public class EmailServiceImpl implements EmailService {

    private String newSubUserSubject;

    public void setNewSubUserSubject(String newSubUserSubject) {
        this.newSubUserSubject = newSubUserSubject;
    }

    @Override
    public void sendEmailForNewSubUser(Job job) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendEmailForPasswordReset(Job job) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
