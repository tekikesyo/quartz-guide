package com.quartz.jobdatamap;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by w1992wishes on 2017/6/6.
 */
public class DumbSchedulerManager {
    public void startScheduler() throws SchedulerException {
        // define the job and tie it to our DumbJob class with JobDataMap
        JobDetail job1 = newJob(DumbJob.class)
                .withIdentity("job1", "group1") // name "myJob", group "group1"
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
                .build();

        // 创建Trigger
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        //设置执行间隔
                        .withIntervalInSeconds(2)
                        //设置执行次数
                        .repeatSecondlyForTotalCount(5))
                .build();

        //创建Scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        //执行调度
        scheduler.scheduleJob(job1, trigger);

        //开始
        scheduler.start();
    }

    public static void main(String[] args) throws SchedulerException {
        DumbSchedulerManager schedulerManager = new DumbSchedulerManager();
        schedulerManager.startScheduler();
    }
}
