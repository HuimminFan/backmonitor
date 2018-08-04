package com.fhm.backmonitor.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * Created by fhm on 2018/7/24.
 * 执行定时任务
 */
public class TestQuartz extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //写监控逻辑
        System.out.println("quart task-当前时间："+new Date());
    }
}
