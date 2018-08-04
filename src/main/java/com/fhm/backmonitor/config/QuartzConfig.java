package com.fhm.backmonitor.config;

import com.fhm.backmonitor.service.TestQuartz;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * Created by fhm on 2018/7/24.
 */

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail testQuartDetail(){
        return JobBuilder.newJob(TestQuartz.class).withIdentity("testQuart").storeDurably().build();
    }

    @Bean
    public Trigger testQuartTrigger(){
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1000).repeatForever();
        return TriggerBuilder.newTrigger().forJob(testQuartDetail()).withIdentity("testQuart").withSchedule(simpleScheduleBuilder).build();

    }
}
