package com.fhm.backmonitor.service.impl;

import com.fhm.backmonitor.entity.DetailReport;
import com.fhm.backmonitor.repository.DetailReportRepository;
import com.fhm.backmonitor.service.DetailReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

/**
 * Created by fhm on 2018/7/27.
 */

@Service
public class DetailReportServiceImpl implements DetailReportService {
    @Autowired
    DetailReportRepository detailReportRepository;

    //根据时间取所有SRP的监控项结果
    @Override
    public List<DetailReport> getDetailReportList(Time createTime) {
        return detailReportRepository.findByCreateTime(createTime);
    }

//    @Transactional
//    public void updateUsers(Long userId,String username){
//        userRepository.updateUserUsername(userId,username);
//    }
}
