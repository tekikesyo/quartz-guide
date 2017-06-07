package com.quartz.triggers;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.dateOf;
import static org.quartz.DateBuilder.evenHourDate;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by w1992wishes on 2017/6/7.
 */
public class SimpleTriggerSchedulerManager {

    public static void main(String[] args) throws SchedulerException {

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        JobDetail job1 = newJob(TriggerJob.class)
                .withIdentity("job1","group1")
                .build();

        Trigger trigger = new SimpleTriggerSchedulerManager().buildTrigger();

        scheduler.scheduleJob(job1, trigger);

        scheduler.start();
    }

    public Trigger buildTrigger(){
        //Build a trigger for a specific moment in time, with no repeats
        // 指定时间开始触发，不重复
        SimpleTrigger trigger1 = (SimpleTrigger)newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(new Date()) // some Date
                .forJob("job1", "group1") // identify job with name, group strings
                .build();

        //Build a trigger for a specific moment in time, then repeating every ten seconds ten times
        //指定时间触发，每隔10秒执行一次，重复10次
        SimpleTrigger trigger2 = (SimpleTrigger)newTrigger()
                .withIdentity("trigger2", "group1")
                .startAt(new Date())
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(10))// note that 10 repeats will give a total of 11 firings
                .forJob("job1", "group1") // identify job with handle to its JobDetail itself
                .build();

        //Build a trigger that will fire once, five minutes in the future
        //5分钟以后开始触发，仅执行一次
        SimpleTrigger trigger3 = (SimpleTrigger) newTrigger()
                .withIdentity("trigger3", "group1")
                .startAt(futureDate(5, DateBuilder.IntervalUnit.MINUTE)) // use DateBuilder to create a date in the future
                .forJob("job1", "group1") // identify job with its JobKey
                .build();

        //Build a trigger that will fire now, then repeat every five minutes, until the hour 22:00
        //立即触发，每个5分钟执行一次，直到22:00
        SimpleTrigger trigger4 = (SimpleTrigger) newTrigger()
                .withIdentity("trigger4", "group1")
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(5)
                        .repeatForever())
                .endAt(dateOf(22, 0, 0))
                .build();

        //Build a trigger that will fire at the top of the next hour, then repeat every 2 hours, forever
        //在下一小时整点触发，每2小时执行一次，一直重复
        SimpleTrigger trigger5 = (SimpleTrigger)newTrigger()
                .withIdentity("trigger5") // because group is not specified, "trigger5" will be in the default group
                .startAt(evenHourDate(null)) // get the next even-hour (minutes and seconds zero ("00:00"))
                .withSchedule(simpleSchedule()
                        .withIntervalInHours(2)
                        .repeatForever())
                // note that in this example, 'forJob(..)' is not called
                //  - which is valid if the trigger is passed to the scheduler along with the job
                .build();

        return trigger2;
    }
}
