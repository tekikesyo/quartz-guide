package com.application.platform.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by w1992wishes on 2017/6/12.
 */
public abstract class AbstractJob implements Job {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected JobDataMap jobDataMap;

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        String name = Thread.currentThread().getName();
        Thread.currentThread().setName(ctx.getJobDetail().getKey().getName());
        jobDataMap = ctx.getJobDetail().getJobDataMap();
        doJob(ctx);
        Thread.currentThread().setName(name);
    }

    public abstract void doJob(JobExecutionContext ctx) throws JobExecutionException;

    @SuppressWarnings({ "unchecked", "hiding" })
    protected <T> T getService(Class<T> clazz) {
        T t = (T) jobDataMap.get(clazz.getSimpleName());
        if (t == null) {
            t = (T) jobDataMap.get(clazz.getSimpleName() + "Impl");
        }
        return t;
    }
}
