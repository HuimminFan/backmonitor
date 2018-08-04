package com.fhm.backmonitor.test;

import com.fhm.backmonitor.entity.User;
import com.fhm.backmonitor.repository.UserRepository;
import com.fhm.backmonitor.service.UserService;
import com.fhm.backmonitor.util.SecurityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuao.tp on 2018/7/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class testUser {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    /*
     *用户管理
     */

    //获取所有用户列表
    @Test
    public void testAllUsers() {
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    //根据手机号获取用户
    public void testGetUserByPhone(){
        User user = userRepository.findByPhone(18300000000L);
        System.err.println(user);
    }

    //根据用户名获取用户
    @Test
    public void getUserListByUsername() {
        User user1 = userRepository.findByUserName("user1");
        System.err.println(user1);

    }

    //新增账户
    @Test
    public void testUserInsert() {
        User user = new User();
        user.setRole(1);
        user.setPhone(18300000000L);
        user.setEmail("xxx@xx.com");
        user.setUserName("wuao1234");
        String usepwd = null;
        try {
            user.setUserPwd(SecurityUtil.md5("123456"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        user.setRemark("user01xxx");

        userRepository.save(user);

        System.out.println(user);
    }

    @Test
    public void testUserInsert2() {

        List<User> users = new ArrayList<>();
        for (int i=10;i<=20;i++){
            User user = new User();
            user.setRole(2);
            user.setPhone(18300000000L);
            user.setEmail("xxx@xx.com");
            user.setUserName("user"+i);
            try {
                user.setUserPwd(SecurityUtil.md5("11111"+i));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            user.setRemark("userxxx");

            users.add(user);
//            userRepository.save(user);
            System.err.println(user);
        }
        userRepository.saveAll(users);
    }

    //删除一个用户（关系表和user表会更新，关联SRP不会被删除）
    @Test
    public void testDelUser() {
        userRepository.deleteById(10000041L);
    }

    //更新某用户密码
    @Test
    public void updateUser() {

//        User user = userRepository.findByUserId(10000032L);
//        if (user!=null){
//            userService.updateUserpwd(10000032L,"zhangsan");
//        }
    }

    //更新某用户信息
    @Test
    public void updateUser1() {
        User user = new User();
        user.setEmail("xxxxxx");
        user.setNickName("xx122");
        user.setRemark("xxxxx");
        user.setUserName("updUser111");
        user.setUserPwd("111111");
        user.setPhone(18900000011L);
        userRepository.saveAndFlush(user);

        System.err.println(user);

    }

    //更新某用户信息
    @Test
    public void updateUser2() {
        User user = userRepository.findByUserId(10000036L);
        user.setRemark("ZZZZZZZZZ");
        userRepository.saveAndFlush(user);
        System.err.println(user);
    }

    //根据srpId获取user信息
    @Test
    public void testFindUsers() {
//        SRP srp = srpRepository.findBySrpId(16L);
        List<User> userList = userService.getUserBySrpId(23L);

        for (User user : userList) {
            System.err.println(user);
        }

    }

    //获取用户列表，包括用户基本信息和srpName
    @Test
    public void testsql() {

        ArrayList<User> users = new ArrayList<>();
        List<Object[]> getuserlist = userRepository.getuserlist();
        for (Object[] objects : getuserlist) {
            User user = new User();
            user.setUserId(Long.valueOf(String.valueOf(objects[0])));
            user.setEmail((String) objects[1]);
            user.setRole((Integer) objects[2]);
            user.setPhone((Long) objects[3]);
            user.setUserPwd((String) objects[4]);
            user.setRemark((String) objects[5]);
            user.setSrpnames(String.valueOf(objects[6]));
            users.add(user);
        }
        for (User user : users) {
            System.err.println(user);
        }
    }

    @Test
    public void testPage() {

        int pageNo = 0;
        int pageSize = 5;

//            pageNo = (pageNo==null || pageNo<0)?0:pageNo;
//            pageSize = (pageSize==null || pageSize<=0)?10:pageSize;
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> page = userRepository.findAll(pageable);

        System.out.println("总记录数: " + page.getTotalElements());
        System.out.println("当前第几页: " + (page.getNumber() + 1));
        System.out.println("总页数: " + page.getTotalPages());
        System.out.println("当前页面的 List: " + page.getContent());
        System.out.println("当前页面的记录数: " + page.getNumberOfElements());
    }
}
