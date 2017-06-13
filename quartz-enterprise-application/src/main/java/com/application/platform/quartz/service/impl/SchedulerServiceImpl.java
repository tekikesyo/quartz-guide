package com.application.platform.quartz.service.impl;

import com.application.platform.quartz.service.SchedulerService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.StringMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.DateFormat;
import java.util.*;

/**
 * Created by w1992wishes on 2017/6/12.
 */
@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService{
    protected static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);
    private Scheduler sched = null;

    @PostConstruct
    public void initialize() {
        logger.info("SchedulerService initialize");
        try {
            SchedulerFactory sf = new StdSchedulerFactory();
            sched = sf.getScheduler();
            sched.start();
        } catch (Exception ex) {
            logger.debug("SchedulerService initialize error", ex);
        }
    }

    @PreDestroy
    public void destroy() {
        logger.info("SchedulerService destroy");
        try {
            sched.shutdown();
        } catch (SchedulerException ex) {
            logger.debug("SchedulerService destroy error", ex);
        }
    }

    public List<Map<String, String>> getJobs() {
        List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
        try {
            for (String name : sched.getTriggerGroupNames()) {
                for (TriggerKey key : sched.getTriggerKeys(new GroupMatcher<TriggerKey>(name,
                        StringMatcher.StringOperatorName.CONTAINS) {
                    private static final long serialVersionUID = 2340465047338422972L;
                })) {
                    Trigger trigger = sched.getTrigger(key);
                    JobDetail job = sched.getJobDetail(trigger.getJobKey());
                    Map<String, String> data = new HashMap<String, String>();
                    datas.add(data);
                    data.put("group", name);
                    data.put("name", job.getKey().getName());
                    data.put("NextFireTime", trigger.getNextFireTime() == null ? "\u505c\u6b62\u6267\u884c"
                            : DateFormat.getDateTimeInstance().format(trigger.getNextFireTime()));
                    data.put("PreviousFireTime",
                            trigger.getPreviousFireTime() == null ? "\u8fd8\u6ca1\u6709\u5f00\u59cb\u6267\u884c"
                                    : DateFormat.getDateTimeInstance().format(trigger.getPreviousFireTime()));
                    data.put("job", job.getJobClass().getName());
                    data.put("trigger", trigger.toString());
                }
            }
        } catch (SchedulerException e) {
            logger.error("", e);
        }
        return datas;
    }

    public void deleteJob(JobKey key) throws SchedulerException {
        sched.deleteJob(key);
    }

    public void scheduleJob(JobDetail job, Trigger trigger) throws SchedulerException {
        sched.scheduleJob(job, trigger);
    }

    public SchedulerContext getContext() throws SchedulerException {
        return sched.getContext();
    }

    public void pauseJob(JobKey key) throws SchedulerException {
        sched.pauseJob(key);
    }

    public void resumeJob(JobKey key) throws SchedulerException {
        sched.resumeJob(key);
    }

    public JobDetail getJobDetail(JobKey key) throws SchedulerException {
        return sched.getJobDetail(key);
    }

    public Trigger getTrigger(TriggerKey triggerKey) throws SchedulerException {
        return sched.getTrigger(triggerKey);
    }

    public void modifySchedualJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        modifyJobTrigger(trigger);
    }

    public void modifyJobDetail(JobDetail jobDetail) throws SchedulerException {
        JobKey jobKey = jobDetail.getKey();
        Trigger trigger = getTrigger(new TriggerKey(jobKey.getName(), jobKey.getGroup()));
        modifySchedualJob(jobDetail, trigger);
    }

    public void modifyJobDataMap(JobKey jobKey, JobDataMap jobDataMap) throws SchedulerException {
        Trigger trigger = getTrigger(new TriggerKey(jobKey.getName(), jobKey.getGroup()));
        JobDetail jobDetail = getJobDetail(jobKey);
        modifySchedualJob(jobDetail, trigger);
    }

    public void modifyJobTrigger(Trigger trigger) throws SchedulerException {
        sched.rescheduleJob(trigger.getKey(), trigger);
    }

    public void triggerJob(JobKey key) throws SchedulerException {
        sched.triggerJob(key);
    }

    public List<Trigger> getTriggersOfJob(JobKey jobKey) throws SchedulerException {
        return (List<Trigger>) sched.getTriggersOfJob(jobKey);
    }

    public Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher) throws SchedulerException {
        return sched.getJobKeys(matcher);
    }

    public void deleteByGroupName(String groupName) {
        GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(groupName);
        try {
            Set<JobKey> jobKeys = getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            logger.error("", e);
        }
    }
}
