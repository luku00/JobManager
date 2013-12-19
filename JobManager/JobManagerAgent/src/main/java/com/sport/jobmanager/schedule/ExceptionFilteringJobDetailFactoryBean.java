package com.sport.jobmanager.schedule;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.MethodInvoker;

/**
 *
 * @author Lukas Kubicek
 */
public class ExceptionFilteringJobDetailFactoryBean extends MethodInvokingJobDetailFactoryBean {

    @Override
    public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException {
        super.afterPropertiesSet();
        JobDetail jobDetail = (JobDetail) super.getObject();
        jobDetail.setJobClass(ExceptionFilteringMethodInvokingJob.class);
    }

    public static class ExceptionFilteringMethodInvokingJob extends QuartzJobBean implements StatefulJob {

        private MethodInvoker methodInvoker;

        public void setMethodInvoker(MethodInvoker methodInvoker) {
            this.methodInvoker = methodInvoker;
        }

        /**
         * This implementation intentionally swallows all exceptions in order to
         * prevent scheduler crash.
         */
        @Override
        protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
            try {
                context.setResult(this.methodInvoker.invoke());
            } catch (Throwable ex) {

            }
        }
    }
}
