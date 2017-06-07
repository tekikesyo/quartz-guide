package com.quartz.triggers;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by w1992wishes on 2017/6/7.
 */
public class TriggerJob implements Job {
    public TriggerJob(){}

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Hello, TriggerJob is executing!");
    }
}
