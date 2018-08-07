package com.fhm.backmonitor.controller;

import com.fhm.backmonitor.entity.Audience;
import com.fhm.backmonitor.entity.ReqUser;
import com.fhm.backmonitor.entity.RespEntity;
import com.fhm.backmonitor.entity.User;
import com.fhm.backmonitor.enums.RespCode;
import com.fhm.backmonitor.repository.UserRepository;
import com.fhm.backmonitor.service.UserService;
import com.fhm.backmonitor.util.JwtHelper;
import com.fhm.backmonitor.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by fhm on 2018/7/19.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService ;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private Audience audience;

    //登录
    @PostMapping("/")
    public RespEntity login(@RequestBody ReqUser reqUser) throws Exception {
        User login = userService.Login(reqUser.getPhone());

        if(login==null )
        {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("用户不存在");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
        else if (!SecurityUtil.md5(reqUser.getUserPwd()).equals(login.getUserPwd())){
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("密码错误");
            respCode.setCode(-2);
            return new RespEntity(respCode);
        }
        else {
            String jwtToken = JwtHelper.createJWT(login.getUserName(),
                    login.getUserId(),
                    login.getRole().toString(),
                    audience.getClientId(),
                    audience.getName(),
                    audience.getExpiresSecond()*1000,
                    audience.getBase64Secret());

            String result_str = "bearer " + jwtToken;

            login.setToken(result_str);//登录成功生成token
            return new RespEntity(RespCode.SUCCESS, login);
        }
    }

    /*根据id取用户*/
    @GetMapping("/getUserById")
    public RespEntity getUserById(@RequestParam("userId") Long userId) throws Exception {
        User userById = userService.getUserById(userId);
        if (userById!=null){
            return new RespEntity(RespCode.SUCCESS,userById);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("根据userId获取用户失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*获取用户列表(admin)*/
    @GetMapping("/userList")
    public RespEntity getList() throws Exception {
        List<User> userList = userService.getUserList();
        if(userList.size()!=0){
            return new RespEntity(RespCode.SUCCESS, userList);
        }
//        System.err.println(userList);
        else{
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("获取用户列表失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*用户新增*/
    @PostMapping("/userInsert")
    public RespEntity userInsert(@RequestBody  ReqUser reqUser){
        User user1 = userService.userInsert(reqUser);
        if (user1!=null){
            return new RespEntity(RespCode.SUCCESS,user1);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("添加用户失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*更新用户信息*/
    @PutMapping("/userUpdate")
    public RespEntity userUpdate(@RequestBody ReqUser user){
        System.err.println(user);
        User user1 = userService.userUpdate(user);

        if (user1!=null){
            return new RespEntity(RespCode.SUCCESS,user1);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("更新用户失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*更新用户密码*/
    @PutMapping ("/updateUserpwd/{UserId}/{oldPwd}/{userPwd}")
    public RespEntity updateUserpwd(@PathVariable Long  UserId, @PathVariable String oldPwd, @PathVariable String userPwd){
        System.err.println(UserId+oldPwd+userPwd);
        User user = userService.updateUserpwd(UserId, oldPwd, userPwd);
        if (user!=null){
            return new RespEntity(RespCode.SUCCESS,user);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("原始密码输入错误");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*重置用户密码*/
    @PostMapping ("/upsetUserpwd/{userId}/{userPwd}")
    public RespEntity upsetUserpwd(@PathVariable Long userId, @PathVariable String userPwd){
        User user1 = userService.upsetUserpwd(userId, userPwd);
        return new RespEntity(RespCode.SUCCESS,user1);
    }

    /*删除一个用户*/
    @DeleteMapping("/delUserById")
    public RespEntity deleteUserById(@RequestParam("userId") Long userId){
        userService.deleteById(userId);
        User user = userRepository.findByUserId(userId);
        if(user==null){
            return new RespEntity(RespCode.SUCCESS);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("删除用户失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*删除多个用户*/
    @DeleteMapping("/delUserList/{userIDs}")
    public RespEntity deleteUserlist(@PathVariable List<Long> userIDs){
        userService.deleteUserlist(userIDs);
        return new RespEntity(RespCode.SUCCESS);
    }

    /*根据srpId获取user列表*/
    @GetMapping("/getUserBySrpId/{SRPID}")
    public RespEntity getUserBySrpId(@PathVariable Long SRPID){
        List<User> users = userService.getUserBySrpId(SRPID);

        if (users!=null){
            return new RespEntity(RespCode.SUCCESS,users);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("根据srpId获取用户失败");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }

    /*判断当前手机号是否已存在（是否已有对应用户）*/
    @GetMapping("/isPhoneRepet/{phone}")
    public RespEntity isPhoneRepet(@PathVariable Long phone){
        User user = userService.isPhoneRepet(phone);
        if (user==null){
            return new RespEntity(RespCode.SUCCESS);
        }
        else {
            RespCode respCode = RespCode.WARN;
            respCode.setMsg("此手机号已有人使用");
            respCode.setCode(-1);
            return new RespEntity(respCode);
        }
    }
}



//    @PostMapping("/user/userlist")
//    public RespEntity login(@RequestBody ReqUser reqUser) throws Exception {
//
//        User login = userService.Login(reqUser.getUserId(), reqUser.getUserPwd());
//        if (login != null) {
//            return new RespEntity(RespCode.SUCCESS, login);
//        } else {
//            RespCode respCode = RespCode.WARN;
//            respCode.setMsg("用户或密码错误");
//            respCode.setCode(-1);
//            return new RespEntity(respCode);
//        }
//    }

//    @PostMapping("/login")
//    public ResultVo login(@RequestParam(value = "userName", required = true) String userName,
//                          @RequestParam(value = "userPwd", required = true) String userPwd,
//                          HttpServletRequest request) {
//
//        User query_user = userService.getUserByName(userName);
//        if (query_user == null) {
//            return ResultVOUtil.error("400", "用户名错误");
//        }
//        //验证密码
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        boolean is_password = encoder.matches(userPwd, query_user.getUserPwd());
//        if (!is_password) {
//            //密码错误，返回提示
//            return ResultVOUtil.error("400", "密码错误");
//        }
//
//        String jwtToken = JwtHelper.createJWT(query_user.getUserName(),
//                query_user.getUserId(),
//                query_user.getRole().toString(),
//                audience.getClientId(),
//                audience.getName(),
//                audience.getExpiresSecond()*1000,
//                audience.getBase64Secret());
//
//        String result_str = "bearer;" + jwtToken;
//
//        System.out.println("************************************");
//        System.out.println(result_str);
//
//        return ResultVOUtil.success(result_str);
//    }