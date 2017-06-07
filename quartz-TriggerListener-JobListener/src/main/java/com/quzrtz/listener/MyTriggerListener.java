package com.quzrtz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * Created by w1992wishes on 2017/6/7.
 */
public class MyTriggerListener implements TriggerListener {
    public String getName() {
        return "myTriggerListener";
    }

     /*
      * 被调度时触发，和它相关的org.quartz.jobdetail即将执行。
      * 该方法优先vetoJobExecution()执行
      */
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        System.out.println("trigger fire");
    }

    /**
     * 被调度时触发，和它相关的org.quartz.jobdetail即将执行。
     */
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        System.out.println("vetoJobException");
        return false;
    }

    /**
     * 被调度时，触发失败时触发
     */
    public void triggerMisfired(Trigger trigger) {
        System.out.println("trigger Misfired");
    }

    /**
     * 执行完毕时触发
     */
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        System.out.println("triggerName complete");
    }
}
