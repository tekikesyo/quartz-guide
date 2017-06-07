package com.quzrtz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * Created by w1992wishes on 2017/6/7.
 */
public class MyJobListener implements JobListener {
    /**
     * 返回当前监听器的名字，这个方法必须被写他的返回值；
     * 因为listener需要通过其getName()方法广播它的名称
     */
    public String getName() {
        return "MyJobListener";
    }

    /**
     * 任务被触发前触发
     */
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        System.out.println("jobToBeExecuted");

    }

    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        System.out.println("jobExecutionVetoed");
    }

    /**
     * 任务调度完成后触发
     */
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        System.out.println("jobWasExecuted");
    }
}
