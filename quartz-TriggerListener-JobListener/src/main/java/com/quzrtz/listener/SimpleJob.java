package com.quzrtz.listener;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by w1992wishes on 2017/6/7.
 */
public class SimpleJob implements Job {
    public SimpleJob(){}

    public void execute(JobExecutionContext context) throws JobExecutionException {
        //打印任务详情
        System.out.println(
                context.getJobDetail().getKey() + "—— is executing");
    }
}
