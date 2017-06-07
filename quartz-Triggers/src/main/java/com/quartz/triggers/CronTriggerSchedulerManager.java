package com.quartz.triggers;

import org.quartz.CronTrigger;
import org.quartz.DateBuilder;

import java.util.TimeZone;

import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;

/**
 * Created by w1992wishes on 2017/6/7.
 */
public class CronTriggerSchedulerManager {
    //CronTrigger instances are built using TriggerBuilder (for the trigger’s main properties) and CronScheduleBuilder (for the CronTrigger-specific properties).
    public void buildTrigger(){
        //Build a trigger that will fire every other minute, between 8am and 5pm, every day
        CronTrigger trigger1 = newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(cronSchedule("0 0/2 8-17 * * ?"))
                .forJob("job1", "group1")
                .build();

        //Build a trigger that will fire daily at 10:42 am
        CronTrigger trigger2 = newTrigger()
                .withIdentity("trigger2", "group")
                .withSchedule(dailyAtHourAndMinute(10, 42))
                .forJob("job1","group1")
                .build();

        //or
        CronTrigger trigger3 = newTrigger()
                .withIdentity("trigger3", "group1")
                .withSchedule(cronSchedule("0 42 10 * * ?"))
                .forJob("job1", "group1")
                .build();

        //Build a trigger that will fire on Wednesdays at 10:42 am
        CronTrigger trigger4 = newTrigger()
                .withIdentity("trigger4", "group1")
                .withSchedule(weeklyOnDayAndHourAndMinute(DateBuilder.WEDNESDAY, 10, 42))
                .forJob("job1","group")
                .build();

        //or
        CronTrigger trigger5 = newTrigger()
                .withIdentity("trigger5", "group1")
                .withSchedule(cronSchedule("0 42 10 ? * WED"))
                .forJob("job1","group1")
                .build();
    }
}
