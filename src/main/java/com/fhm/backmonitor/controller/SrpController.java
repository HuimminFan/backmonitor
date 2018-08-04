package com.fhm.backmonitor.controller;

import com.fhm.backmonitor.entity.MonitorItem;
import com.fhm.backmonitor.entity.RespEntity;
import com.fhm.backmonitor.entity.SRP;
import com.fhm.backmonitor.entity.User;
import com.fhm.backmonitor.enums.RespCode;
import com.fhm.backmonitor.repository.MonitorItemRepository;
import com.fhm.backmonitor.repository.SrpRepository;
import com.fhm.backmonitor.service.MonitorItemService;
import com.fhm.backmonitor.service.SRPService;
import com.fhm.backmonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by fhm on 2018/7/19.
 */
@RestController
public class SrpController {

    @Autowired
    SRPService srpService;
    @Autowired
    SrpRepository srpRepository;
    @Autowired
    UserService userService;
    @Autowired
    MonitorItemService monitorItemService;
    @Autowired
    MonitorItemRepository monitorItemRepository;

    /*获取SRP列表*/
    @PostMapping("/srpList")
    public RespEntity getsrpList(@RequestBody User user) {
        System.err.println(user);
        if (user.getRole()==1){
            List<SRP> srpList = srpService.getsrpList();
            if(srpList!=null){
                return new RespEntity(RespCode.SUCCESS, srpList);
            }
            else{
                RespCode respCode = RespCode.WARN;
                respCode.setMsg("获取SRP列表失败");
                respCode.setCode(-1);
                return new RespEntity(respCode);
            }
        }
        else {
            List<SRP> srpList = srpService.findByUserId(user.getUserId());
            if(srpList!=null){
                return new RespEntity(RespCode.SUCCESS, srpList);
            }
            else{
                RespCode respCode = RespCode.WARN;
                respCode.setMsg("获取SRP列表失败");
                respCode.setCode(-1);
                return new RespEntity(respCode);
            }
        }

    }
    /*根据srpId获取SRP*/
    @GetMapping("/getSrpById")
    public RespEntity getUserById(@RequestParam("srpId") Long srpId) throws Exception {
        SRP srp = srpService.getSrpById(srpId);
        if (srp!=null){
            return new RespEntity(RespCode.SUCCESS,srp);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("根据srpId获取SRP失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*SRP新增*/
    @PostMapping("/srpInsert")
    public RespEntity srpInsert(@RequestBody SRP srp){
        SRP srp1 = srpService.srpInsert(srp);

        if (srp1!=null){
            return new RespEntity(RespCode.SUCCESS,srp1);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("添加SRP失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*更新SRP信息*/
    @PutMapping("/srpUpdate")
    public RespEntity srpUpdate(@RequestBody SRP srp){
        SRP srp1 = srpService.srpUpdate(srp);

        if (srp1!=null){
            return new RespEntity(RespCode.SUCCESS,srp1);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("更新SRP失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*给SRP加所属用户*/
    @PostMapping("/userAdd")
    public RespEntity userAdd(@PathVariable Long srpId, @PathVariable List<Long> userIds){
        int userCount = srpService.userAdd(srpId, userIds);
        if (userCount>0){
            return new RespEntity(RespCode.SUCCESS,userCount);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("给SRP添加所属用户失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*给SRP减所属用户*/
    @PostMapping("/userSub")
    public RespEntity userSub(@PathVariable Long srpId, Long userId){
        int userCount = srpService.userSub(srpId,userId);
        if (userCount<0){
            return new RespEntity(RespCode.SUCCESS,userCount);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("给SRP删除用户失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*删除一个SRP*/
    @DeleteMapping("/delSrpById")
    public RespEntity deleteSrpById(@RequestParam("srpId") Long srpId){
        RespCode respCode1 = srpService.deleteById(srpId);

        if(srpRepository.findBySrpId(srpId)==null){
            return new RespEntity(respCode1);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("删除SRP失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*删除多个SRP*/
    @DeleteMapping("/delSrpList")
    public RespEntity deleteUserlist(@RequestParam("srpIds") List<Long> srpIds){
        srpService.deleteSrplist(srpIds);
        return new RespEntity(RespCode.SUCCESS);
    }

    /*显示用户列表（给SRP加用户时选用户）*/
    @GetMapping("/users")
    public RespEntity getAllUsers() throws Exception {
        List<User> users = srpService.getUserList();
        if(users.size()!=0){
            return new RespEntity(RespCode.SUCCESS, users);
        }
        else{
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("显示用户列表失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*根据srpId获取user列表*/
    @PutMapping("/getUserBySrpId")
    public RespEntity getUserBySrpId(@RequestParam("srpId") Long srpId){
        List<User> users = userService.getUserBySrpId(srpId);

        if (users!=null){
            return new RespEntity(RespCode.SUCCESS,users);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("根据srpId获取用户列表失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*给SRP加监控项*/
    @PostMapping("/monitorItemAdd")
    public RespEntity monitorItemAdd(@PathVariable Long srpId, @PathVariable Long monitorItemId){
        SRP srp = srpService.monitorItemAdd(srpId, monitorItemId);

        if (srp!=null){
            return new RespEntity(RespCode.SUCCESS,srp);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("给SRP添加监控项失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*给SRP减监控项*/
    @DeleteMapping("/monitorItemSub")
    public RespEntity monitorItemSub(@RequestParam("monitorItemId") Long monitorItemId){
        srpService.monitorItemSub(monitorItemId);
        MonitorItem monitorItem = monitorItemRepository.findByMonitorId(monitorItemId);
        if(monitorItem==null){
            return new RespEntity(RespCode.SUCCESS);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("删除监控项失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    //监控项详细配置查看的接口在MonitorItemController文件中
    //@GetMapping("/getMonitorById")

    //SRP下监控项列表查看的接口在MonitorItemController文件中
    //@GetMapping("/getMonitorsBySrpId")
}
