package com.fhm.backmonitor.service.impl;

import com.fhm.backmonitor.entity.MonitorItem;
import com.fhm.backmonitor.repository.MonitorItemRepository;
import com.fhm.backmonitor.repository.SrpRepository;
import com.fhm.backmonitor.service.MonitorItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fhm on 2018/7/27.
 */

@Service
public class MonitorItemServiceImpl implements MonitorItemService {
    @Autowired
    MonitorItemRepository monitorItemRepository;
    @Autowired
    SrpRepository srpRepository;

    /*根据srpId查所有监控项，并根据classify排序(显示srp的监控项列表)*/
    @Override
    public List<MonitorItem> getMonitTtemListBySrpId(Long srpId) {
        List<MonitorItem> monitorItemList = monitorItemRepository.findBySrpIdOrderByClassify(srpId);
        return monitorItemList;
    }

    /*通过id获取监控项*/
    @Override
    public MonitorItem getMonitorItemListById(Long monitorItemId) {
        MonitorItem monitorItem = monitorItemRepository.findByMonitorId(monitorItemId);
        return monitorItem;
    }

    /*新增监控项*/
    @Transactional
    @Override
    public MonitorItem monitorItemInsert(MonitorItem monitorItem) {
//        MonitorItem monitorItem1 = new MonitorItem();
//
//        monitorItem1.setMonitorType(monitorItem.getMonitorType());
//        monitorItem1.setMonitorName(monitorItem.getMonitorName());
//        monitorItem1.setRemark(monitorItem.getRemark());
//        monitorItem1.setUrl(monitorItem.getUrl());
//        monitorItem1.setRequestType(monitorItem.getRequestType());
//        monitorItem1.setRequestBody(monitorItem.getRequestBody());
//        monitorItem1.setAsserts(monitorItem.getAsserts());
//        monitorItem1.setClassify(monitorItem.getClassify());
//        monitorItem1.setSrpId(monitorItem.getSrpId());

//        SRP srp = monitorItem.getSrp();
//        monitorItem1.setSrp(srp);
//        srp.getMonitorItems().add(monitorItem1);
//
//        srpRepository.save(srp);
        MonitorItem save = monitorItemRepository.save(monitorItem);
        return save;
    }

    /*更新监控项*/
    @Transactional
    @Override
    public MonitorItem monitorItemUpdate(MonitorItem monitorItem) {
//        MonitorItem monitorItem1 = monitorItemRepository.findByMonitorId(monitorItem.getMonitorId());
        if (monitorItem!=null){
//            monitorItem1.setMonitorName(monitorItem.getMonitorName());
//            monitorItem1.setRemark(monitorItem.getRemark());
//            monitorItem1.setUrl(monitorItem.getUrl());
//            monitorItem1.setRequestType(monitorItem.getRequestType());
//            monitorItem1.setRequestBody(monitorItem.getRequestBody());
//            monitorItem1.setAsserts(monitorItem.getAsserts());
            return monitorItemRepository.saveAndFlush(monitorItem);
        }
        return null;
    }

    /*删除一个监控项*/
    @Transactional
    @Override
    public void deleteById(Long monitorItemId) {
        if (monitorItemRepository.findByMonitorId(monitorItemId)!=null)
            monitorItemRepository.deleteById(monitorItemId);
    }

    /*删除多个监控项*/
    @Transactional
    @Override
    public void deletemonitorItemlist(List<Long> monitorItemIds) {
        for (Long monitorItemId : monitorItemIds){
            monitorItemRepository.deleteById(monitorItemId);
        }
    }
}
