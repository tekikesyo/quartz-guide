package com.application.service;

import org.quartz.SchedulerException;

import java.text.ParseException;

/**
 * Created by w1992wishes on 2017/6/12.
 */
public interface TestService {
    void resetTrigger(String triggerTime) throws ParseException, SchedulerException;
}
