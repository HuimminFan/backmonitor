package com.fhm.backmonitor.repository;


import com.fhm.backmonitor.entity.MonitorItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by wuao.tp on 2018/7/18.
 */
public interface MonitorItemRepository extends JpaRepository<MonitorItem,Long> {

    //根据SRPid查
    //    @Override
    //    List<MonitorItem> findAll();

    /*根据srpId查所有监控项，并根据classify排序(显示srp的监控项列表)*/
    List<MonitorItem> findBySrpIdOrderByClassify(Long srpId);

    /*通过id获取监控项*/
    MonitorItem findByMonitorId(Long mobitorItemId);
}
