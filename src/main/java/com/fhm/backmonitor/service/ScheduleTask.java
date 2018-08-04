package com.fhm.backmonitor.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by wuao.tp on 2018/7/23.
 */
@Component
public class ScheduleTask  {
    private  static int time =0;
    @Scheduled(fixedRate = 1000)  //一秒
    public void reportCurrentTime(){
        time=(time+10)%60;
        System.out.println(time);
    }
}