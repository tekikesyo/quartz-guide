package com.application.platform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 业务层基类.
 *
 * Created by w1992wishes on 2017/6/12.
 */
public abstract class BaseService implements Service {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void initialize() {
        if (logger.isInfoEnabled()) {
            logger.info("Invoke " + getClass() + " initialize method...");
        }
    }

    @PreDestroy
    public void destroy() {
        if (logger.isInfoEnabled()) {
            logger.info("Invoke " + getClass() + " destroy method...");
        }
    }

    public void start() {
    }

    public void stop() {
    }

    /**
     * 得到系统运行的日志记录器.
     *
     * @return Logger
     */
    public final Logger getLogger() {
        return logger;
    }
}

