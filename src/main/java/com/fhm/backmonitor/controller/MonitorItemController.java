package com.fhm.backmonitor.controller;

import com.fhm.backmonitor.entity.MonitorItem;
import com.fhm.backmonitor.entity.RespEntity;
import com.fhm.backmonitor.enums.RespCode;
import com.fhm.backmonitor.repository.MonitorItemRepository;
import com.fhm.backmonitor.repository.SrpRepository;
import com.fhm.backmonitor.service.MonitorItemService;
import com.fhm.backmonitor.service.SRPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by fhm on 2018/7/19.
 */
@RestController
public class MonitorItemController {

    @Autowired
    MonitorItemRepository monitorItemRepository;
    @Autowired
    MonitorItemService monitorItemService;
    @Autowired
    SRPService srpService;
    @Autowired
    SrpRepository srpRepository;


    /*通过id获取监控项(监控项详细配置查看)*/
    @GetMapping("/getMonitorById")
    public RespEntity getMonitorById(@RequestParam("monitorItemId") Long monitorItemId) {
        MonitorItem monitorItem = monitorItemService.getMonitorItemListById(monitorItemId);

        if (monitorItem!=null){
            return new RespEntity(RespCode.SUCCESS,monitorItem);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("根据Id获取monitorItem失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*根据srpId查所有监控项，并根据classify排序(显示srp的监控项列表)*/
    @GetMapping("/getMonitorsBySrpId")
    public RespEntity getMonitorsBySrpId(@RequestParam("srpId") Long srpId) {
        List<MonitorItem> monitTtemListBySrpId = monitorItemService.getMonitTtemListBySrpId(srpId);

        if (monitTtemListBySrpId!=null){
            return new RespEntity(RespCode.SUCCESS,monitTtemListBySrpId);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("根据srpId获取monitorItems失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*监控项新增*/
    @PostMapping("/monitorItemInsert")
    public RespEntity monitorItemInsert(@RequestBody MonitorItem monitorItem){
        MonitorItem monitorItem1 = monitorItemService.monitorItemInsert(monitorItem);
        if (monitorItem1!=null){
            return new RespEntity(RespCode.SUCCESS,monitorItem1);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("添加监控项失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*更新监控项信息*/
    @PutMapping("/monitorItemUpdate")
    public RespEntity monitorItemUpdate(@RequestBody MonitorItem monitorItem){
        MonitorItem monitorItem1 = monitorItemService.monitorItemUpdate(monitorItem);
        if (monitorItem1!=null){
            return new RespEntity(RespCode.SUCCESS,monitorItem1);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("更新监控项失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*删除一个监控项*/
    @DeleteMapping("/deletemonitorItemById")
    public RespEntity deletemonitorItemById(@RequestParam("monitorItemId") Long monitorItemId){
        monitorItemService.deleteById(monitorItemId);
        if (monitorItemRepository.findByMonitorId(monitorItemId)!=null)
            return new RespEntity(RespCode.SUCCESS);
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("删除监控项失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*删除多个监控项*/
    @DeleteMapping("/delemonitorItemList/{monitorItemIds}")
    public RespEntity deletemonitorItemlist(@PathVariable List<Long> monitorItemIds){
        monitorItemService.deletemonitorItemlist(monitorItemIds);
        return new RespEntity(RespCode.SUCCESS);
    }
}
