package com.sport.jobmanager.serviceimpl;

import com.sport.jobmanager.common.domain.Job;
import com.sport.jobmanager.service.EmailService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import org.springframework.mail.javamail.JavaMailSender;

/**
 *
 * @author Lukas Kubicek
 */
public class EmailServiceImpl implements EmailService {

    private String newSubUserSubject;
    private JavaMailSender mailSender;
    private Map<String, String> templates;

    public void setNewSubUserSubject(String newSubUserSubject) {
        this.newSubUserSubject = newSubUserSubject;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplates(Map<String, String> templates) {
        this.templates = templates;
    }

    @Override
    public void sendEmailForNewSubUser(Job job) {
        String test = getBodyFromTemplate(templates.get("newSubUser"));
    }

    @Override
    public void sendEmailForPasswordReset(Job job) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String getBodyFromTemplate(String path) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(path)));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            String ee = e.getLocalizedMessage();
        }
        return contentBuilder.toString();
    }
}
