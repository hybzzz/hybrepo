package com.hyb.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by admin on 2018/4/4.
 */
public class TestJob implements Job {

    private static Logger log = LoggerFactory.getLogger(TestJob.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("----------------------");
        System.out.println("测试任务开始");
    }
}
