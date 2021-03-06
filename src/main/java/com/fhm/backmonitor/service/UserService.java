package com.fhm.backmonitor.service;


import com.fhm.backmonitor.entity.ReqUser;
import com.fhm.backmonitor.entity.User;

import java.util.List;

/**
 * Created by fhm on 2018/7/23.
 */

public interface UserService {
    /*登录*/
    User Login(Long phone) throws Exception;

    /*根据userId获取用户*/
    User getUserById(Long id) throws Exception;

    /*获取用户列表，包括用户基本信息和srpName*/
    List<User> getUserList( )throws Exception;

    /*新增账户*/
    User userInsert(ReqUser reqUser);

    /*更新账户*/
    User userUpdate(ReqUser user);

    /*删除一个用户*/
    void deleteById(Long Long);

    /*删除多个用户*/
    void deleteUserlist(List<Long> userIDs);

    /*更新用户密码*/
    User updateUserpwd(Long userId,String oldPwd, String userPwd);

    /*重置用户密码*/
    User upsetUserpwd(Long userId, String userPwd);

    //根据srpId获取user列表,包括关联srp信息name等
    List<User> getUserBySrpId(Long srpId);

    /*判断当前手机号是否已存在（是否已有对应用户）*/
    User isPhoneRepet(Long phone);

    void saveUsers(List<User> users) throws Exception;

    User getUserByName(String userName);
}
