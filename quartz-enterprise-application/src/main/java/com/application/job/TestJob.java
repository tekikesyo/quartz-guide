package com.application.job;

import com.application.platform.quartz.job.AbstractJob;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by w1992wishes on 2017/6/12.
 */
public class TestJob extends AbstractJob {
    protected static final Logger logger = LoggerFactory.getLogger(TestJob.class);

    public void doJob(JobExecutionContext ctx) throws JobExecutionException {
        JobDetail jobDetail = ctx.getJobDetail();
        System.out.println(jobDetail.getKey().getName() + " : " + jobDetail.getKey().getGroup() + " with the time is : " + new Date().toString());
    }
}
