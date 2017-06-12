package com.application.service.impl;

import com.application.job.TestJob;
import com.application.platform.quartz.service.SchedulerService;
import com.application.platform.service.BaseService;
import com.application.service.TestService;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by w1992wishes on 2017/6/12.
 */
public class TestServiceImpl extends BaseService implements TestService {

    @Autowired
    private SchedulerService schedulerService;

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void start() {
        try {
            newCollectJob();
        } catch (SchedulerException e) {
            logger.error("", e);
        }
    };

    public void resetTrigger(String triggerTime) throws SchedulerException, ParseException {
        TriggerKey triggerKey = new TriggerKey("testTrigger", "testGroup");
        CronTriggerImpl trigger = (CronTriggerImpl)schedulerService.getTrigger(triggerKey);
        //如果job存在则修改定期执行时间，如果不存在则创建一个新的job
        if ( trigger != null) {
            trigger.setCronExpression(bulidCronExpression(triggerTime));
            schedulerService.modifyJobTrigger(trigger);
        }else {
            newCollectJob();
        }
    }

    /**
     * 触发job
     *
     * @throws SchedulerException
     */
    public void newCollectJob() throws SchedulerException {
        JobDetail job = newJob(TestJob.class)
                .withIdentity("testJob","testGroup")
                .build();
        Trigger trigger = newTrigger()
                .withIdentity("testTrigger", "testGroup")
                .forJob(job)
                .withSchedule(cronSchedule(bulidCronExpression("3:00")))
                .build();

        schedulerService.scheduleJob(job, trigger);
    }

    /**
     * 执行任务的时间表达式，这里是 每天 某时某分 执行
     *
     * @param triggerTime 3:00
     */
    public String bulidCronExpression(String triggerTime) {
        String[] time = triggerTime.split(":");
        StringBuilder sb = new StringBuilder();
        sb.append("0 ").append(time[1]).append(" ").append(time[0]).append(" * * ?");
        return sb.toString();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
