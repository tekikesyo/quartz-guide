package com.quzrtz.listener;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.GroupMatcher.jobGroupEquals;
import static org.quartz.impl.matchers.GroupMatcher.triggerGroupEquals;

/**
 * Created by w1992wishes on 2017/6/7.
 */
public class ListenerTest {

    public static void main(String[] args) throws SchedulerException {
        //第一步：创建一个JobDetail实例
        JobDetail job = newJob(SimpleJob.class)
                .withIdentity("job1", "group1")
                .build();

        //第二步：通过SimpleTrigger触发器定义调度规则：马上启动，每2秒运行一次，共运行10次
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(simpleSchedule()
                    .withIntervalInSeconds(2)
                    .withRepeatCount(10))
                .build();

        //第三步：通过SchedulerFactory获取一个调度器实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        //第四步：关联监听器
        scheduler.getListenerManager().addJobListener(new MyJobListener(),jobGroupEquals("group1"));
        scheduler.getListenerManager().addTriggerListener(new MyTriggerListener(), triggerGroupEquals("group1"));

        //第五步：将job跟trigger注册到scheduler中进行调度
        scheduler.scheduleJob(job, trigger);

        //第六步：调度启动
        scheduler.start();
    }
}
