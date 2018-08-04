package com.fhm.backmonitor.service;


import com.fhm.backmonitor.entity.SRP;
import com.fhm.backmonitor.entity.User;
import com.fhm.backmonitor.enums.RespCode;

import java.util.List;

/**
 * Created by fhm on 2018/7/23.
 */

public interface SRPService {

    //获取SRP列表
    List<SRP> getsrpList();

    /*根据srpId获取SRP*/
    SRP getSrpById(Long srpId);

    //新增SRP
    SRP srpInsert(SRP srp);

    //更新SRP
    SRP srpUpdate(SRP srp);

    /*给SRP加所属用户*/
    int userAdd(Long srpId, List<Long> userIds);

    /*给SRP减所属用户*/
    int userSub(Long srpId, Long userId);

    /*删除一个SRP*/
    RespCode deleteById(Long srpId);

    /*删除多个SRP*/
    void deleteSrplist(List<Long> srpIDs);

    /*根据userId获取srp*/
    List<SRP> findByUserId(Long userId);

    /*显示用户列表*/
    List<User> getUserList();

    /*给SRP加监控项*/
    SRP monitorItemAdd(Long srpId, Long monitorItemId);

    /*给SRP减监控项*/
    void monitorItemSub(Long monitorItemId);

    /*给SRP删除多个监控项*/
    void monitorItemListSub(List<Long> monitorItemIds);

    //根据srpId获取监控项列表
}
