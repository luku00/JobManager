package com.sport.jobmanager.serviceimpl;

import com.sport.jobmanager.common.domain.Job;
import com.sport.jobmanager.service.EmailService;
import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 *
 * @author Lukas Kubicek
 */
public class EmailServiceImpl implements EmailService {

    static Logger LOGGER = LoggerFactory.getLogger("agent");

    private String encoding;
    private String sentFrom;
    private String newSubUserSubject;
    private String resetPasswordSubject;
    private String newPlanSubject;
    private String resetPasswordBaseUrl;
    private String forgotPasswordBaseUrl;
    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    private Map<String, String> templates;

    public void setForgotPasswordBaseUrl(String forgotPasswordBaseUrl) {
        this.forgotPasswordBaseUrl = forgotPasswordBaseUrl;
    }

    public void setNewSubUserSubject(String newSubUserSubject) {
        this.newSubUserSubject = newSubUserSubject;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplates(Map<String, String> templates) {
        this.templates = templates;
    }

    public void setResetPasswordBaseUrl(String resetPasswordBaseUrl) {
        this.resetPasswordBaseUrl = resetPasswordBaseUrl;
    }

    public void setResetPasswordSubject(String resetPasswordSubject) {
        this.resetPasswordSubject = resetPasswordSubject;
    }

    public void setNewPlanSubject(String newPlanSubject) {
        this.newPlanSubject = newPlanSubject;
    }

    @Override
    public void sendEmailForNewSubUser(final Job job) {
        Map model = new HashMap();
        model.put("user", job.getUserFirstName() + " " + job.getUserLastName());
        model.put("link", forgotPasswordBaseUrl);
        model.put("login", job.getUserLogin());

        sendEmail("newSubUser", newSubUserSubject, job, model);
    }

    @Override
    public void sendEmailForPasswordReset(Job job) throws Exception {
        if (job.getJobIdentifier() == null) {
            throw new Exception("missing job identifier");
        }
        String link = resetPasswordBaseUrl + "/" + job.getJobIdentifier();

        Map model = new HashMap();
        model.put("user", job.getUserFirstName() + " " + job.getUserLastName());
        model.put("link", link);

        sendEmail("resetPassword", resetPasswordSubject, job, model);
    }

    private void sendEmail(final String template, final String subject, final Job job, final Map model) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(job.getUserEmail());
                message.setFrom(sentFrom);
                message.setSubject(subject);
                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, templates.get(template), encoding, model);
                message.setText(text, true);
            }
        };

        mailSender.send(preparator);
    }

    @Override
    public void sendEmailForNewPlan(Job job) {
        Map model = new HashMap();
        model.put("user", job.getUserFirstName() + " " + job.getUserLastName());
        model.put("fromDate", job.getFromDate().toString());
        model.put("toDate", job.getToDate().toString());
        model.put("goal", job.getGoalValue());
        model.put("goalType", job.getGoalType());
        model.put("reward", job.getReward());

        sendEmail("newPlan", newPlanSubject, job, model);
    }
}
