package com.application.test;

import com.application.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;

import static java.lang.System.*;

/**
 * Created by w1992wishes on 2017/6/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ServiceTest {
    @Autowired
    private TestService testService;

    @Test
    public void testService() throws ParseException, SchedulerException {
        testService.resetTrigger("13:05");
//        try {
//            Thread.currentThread().sleep(1000*60);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
