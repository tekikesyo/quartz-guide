TriggerListener And JobListener
================================
    Listeners are objects that you create to perform actions based on events occurring within the scheduler. As you can probably guess, TriggerListeners receive events related to triggers, and JobListeners receive events related to jobs.
### 一、TriggerListener
Trigger-related events include: trigger firings, trigger mis-firings (discussed in the “Triggers” section of this document), and trigger completions (the jobs fired off by the trigger is finished).

**The org.quartz.TriggerListener Interface**

    public interface TriggerListener {
    
        public String getName();
    
        public void triggerFired(Trigger trigger, JobExecutionContext context);
    
        public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context);
    
        public void triggerMisfired(Trigger trigger);
    
        public void triggerComplete(Trigger trigger, JobExecutionContext context,
                int triggerInstructionCode);
    }
    
###　二、JobListener
Job-related events include: a notification that the job is about to be executed, and a notification when the job has completed execution.

**The org.quartz.JobListener Interface**

    public interface JobListener {

    public String getName();

    public void jobToBeExecuted(JobExecutionContext context);

    public void jobExecutionVetoed(JobExecutionContext context);

    public void jobWasExecuted(JobExecutionContext context,
            JobExecutionException jobException);
    }
    
### 三、Using Your Own Listeners
可以通过两种方式定义自己的Listener
1. 一种是implement TriggerListener and/or org.quartz.JobListener两个接口；
2. 一种是extends JobListenerSupport or TriggerListenerSupport

Adding a JobListener that is interested in a particular job:

    scheduler.getListenerManager().addJobListener(myJobListener, KeyMatcher.jobKeyEquals(new JobKey("myJobName", "myJobGroup")));
    
### 四、官方资料资料
http://www.quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/tutorial-lesson-07.html