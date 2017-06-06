package com.quartz.quick.started;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by w1992wishes on 2017/6/6.
 */
public class SimpleJob implements Job {
    public SimpleJob(){}

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Hello World, Simple Job is executing!");
    }
}
