package com.quartz.quick.started;


import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

/**
 * Schedulers can be immediately used to schedule jobs,
 * but they will not start executing any until the .start() method has been called.
 *
 * Created by w1992wishes on 2017/6/6.
 */
public class SimpleSchedulerManager {

    public static void main(String[] args) throws SchedulerException {
        // Grab the SimpleSchedulerManager instance from the Factory
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // and start it off
        scheduler.start();

        // define the job and tie it to our SimpleJob class
        JobDetail job = newJob(SimpleJob.class)
                .withIdentity("job1", "group1")
                .build();

        // Trigger the job to run now, and then repeat every 40 seconds
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, trigger);
    }
}
