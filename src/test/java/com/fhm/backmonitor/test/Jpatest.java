package com.fhm.backmonitor.test;

import com.fhm.backmonitor.entity.MonitorItem;
import com.fhm.backmonitor.entity.SRP;
import com.fhm.backmonitor.entity.User;
import com.fhm.backmonitor.repository.MonitorItemRepository;
import com.fhm.backmonitor.repository.SrpRepository;
import com.fhm.backmonitor.repository.UserRepository;
import com.fhm.backmonitor.service.SRPService;
import com.fhm.backmonitor.service.UserService;
import com.fhm.backmonitor.util.SecurityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wuao.tp on 2018/7/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class Jpatest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SRPService srpService;
    @Autowired
    SrpRepository srpRepository;
    @Autowired
    UserService userService;
    @Autowired
    MonitorItemRepository monitorItemRepository;

    @Test
    public void getUserList() {
        List<User> all = userRepository.findAll();
        for (User user : all) {
            System.out.println("user = " + user.getUserName());
        }
    }

    @Test
    public void getUserListByUsername() {
        User user = new User();
        user.setUserName("wuao");
        Example<User> example = Example.of(user);
        List<User> all = userRepository.findAll(example);
        for (User user1 : all) {
            System.out.println("user1 = " + user1.getUserName());
        }
//        System.out.println(userRepository.findById((long) 111).isPresent());

    }


    @Test
    public void insertUser() {
        User user = new User();
        user.setEmail("130278228@qq.com");
        user.setUserName("wuao");
        user.setUserPwd("123456");
        User save = userRepository.save(user);
        System.out.println(save);
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


    //多对多保存
    @Test
    public void testmtmsave() {
        User user7 = new User();
        SRP srp7 = new SRP();
        SRP srp8 = new SRP();

        user7.setUserName("u007");
        user7.setUserPwd("111111");
        srp7.setSrpName("s007");
        srp8.setSrpName("s008");

        //设置关联关系
        user7.getSrps().add(srp7);
        user7.getSrps().add(srp8);
        srp7.getUsers().add(user7);
        srp8.getUsers().add(user7);

        userRepository.save(user7);
        srpRepository.save(srp7);
        srpRepository.save(srp8);
    }

    /*
     *SRP管理
     */

    //根据userId获取SRPname(可用在某SRP接收者列表)
    @Test
    public void testFindSrps() {
        List<SRP> srpList = srpService.findByUserId(10000032L);
        for (SRP srp : srpList) {
            System.err.println(srp);
        }
    }

    //获取所有SRP列表
    @Test
    public void testAllSrps() {
        List<SRP> srpList = srpRepository.findAll();
        for (SRP srp : srpList) {
//            System.out.println(srp.getSrpId()+":"+srp.getSrpName());
            System.err.println(srp);
        }
    }

    //SRP新增
    @Test
    public void testSrpInsert() {
        SRP srp = new SRP();
        srp.setSrpName("yayaya");
        srp.setDescription("XXXXXXXXXXyayayaXXXXXXXXXXXXX");
        srp.setSwitchs(true);
        srp.setFreq(20);

        srpRepository.save(srp);
    }

    @Test
    public void testSrpInsert2() {
        for (int i = 1 ; i <= 10 ; i ++){
            SRP srp = new SRP();
            srp.setSrpName("srp"+i);
            srp.setDescription("XXXXXXXXXXyayayaXXXXXXXXXXXXX");
            srp.setSwitchs(true);
            srp.setFreq(20);
            srpRepository.save(srp);
        }
    }



    //SRP更新
    @Test
    public void testSrpUpdate() {
        SRP srp1 = srpRepository.findBySrpId(18L);

//        srp1.setSrpName("updateName");
        srp1.setDescription("ddddddddddddddddddd");

        srpRepository.saveAndFlush(srp1);

    }

    //给SRP加所属用户
    @Test
    public void testSrpUserInsert() {
        User u1 = userRepository.findByUserId(10000053L);
        User u2 = userRepository.findByUserId(10000054L);

        SRP srpnew = srpRepository.findBySrpId(49L);

        srpnew.getUsers().addAll(Arrays.asList(u1, u2));
//        srpnew.getUsers().add(u1);
//        srpnew.getUsers().add(u2);
        u1.getSrps().add(srpnew);
        u2.getSrps().add(srpnew);
//
        srpRepository.save(srpnew);
        userRepository.save(u1);
        userRepository.save(u2);
    }

    //给SRP减所属用户
//    @Transactional
    @Test
    public void testSrpUserSub() {
        User uu = userRepository.findByUserId(10000021L);

        SRP srpsub = srpRepository.findBySrpId(23L);

//        srpsub.getUsers().clear();
//        uu.getSrps().clear();

        System.err.println(uu.getSrps().size());

        for (SRP srp : uu.getSrps()) {
            System.err.println(srp);
        }

        System.err.println(uu.getSrps().contains(srpsub));

        uu.getSrps().remove(srpsub);
        srpsub.getUsers().clear();

        System.err.println(uu.getSrps().contains(srpsub));

        srpRepository.saveAndFlush(srpsub);
        userRepository.saveAndFlush(uu);

        System.err.println(uu.getSrps().contains(srpsub));

//        srpRepository.save(srpsub);
//        userRepository.save(uu);
    }

    @Test
    public void testSrpUserSub2() {
        User u1 = userRepository.findByUserId(10000053L);
        User u2 = userRepository.findByUserId(10000054L);
//        SRP srp = srpRepository.findBySrpId(48L);
        SRP srp = u1.getSrps().iterator().next();
        SRP srp1 = u2.getSrps().iterator().next();

//        for (User user : srp.getUsers()){
//            user.getSrps().remove(srp);
//        }
        u1.getSrps().remove(srp);
        u2.getSrps().remove(srp1);

        srpRepository.saveAndFlush(srp);
        userRepository.saveAndFlush(u1);
        userRepository.saveAndFlush(u2);

//        Category ctg = em.find(Category.class, 3);
//        Item item = ctg.getItemsSet().iterator().next();
//        ctg.getItemsSet().remove(item);
    }

    //成功
    @Test
    public void testSrpUserSub3() {
        User u1 = userRepository.findByUserId(10000054L);
//        SRP srp = srpRepository.findBySrpId(48L);
        SRP srp = u1.getSrps().iterator().next();

        u1.getSrps().remove(srp);
        srp.getUsers().remove(u1);

//        System.err.println(srp);
//        System.err.println(srp.getUsers());

        srpRepository.saveAndFlush(srp);
        userRepository.saveAndFlush(u1);
    }

    //给SRP加监控项
    @Test
    public void testItemAdd() {
        SRP srp = srpRepository.findBySrpId(48L);
        MonitorItem monitorItem3 = new MonitorItem();
//        MonitorItem monitorItem3 = monitorItemRepository.findByMonitorId(1L);
//        MonitorItem monitorItem4 = monitorItemRepository.findByMonitorId(3L);
        MonitorItem monitorItem2 = new MonitorItem();

        monitorItem2.setMonitorName("API");
        monitorItem2.setRemark("API111111111111");
        monitorItem2.setUrl("http://api-pataciot-acniotsense.wise-paas.com.cn/api/v1.0/authentication/login/phone");
        monitorItem2.setRequestType(1);
        monitorItem2.setRequestBody("{\n" +
                "    \"password\":\"123456\",\n" +
                "    \"phone\":\"13900000000\",\n" +
                "    \"tokenTs\":20160\n" +
                "}\n");
        monitorItem2.setAsserts("{\n" +
                "    \"statusCode\":\"200\"\n" +
                "}");

        monitorItem3.setMonitorName("API");
        monitorItem3.setRemark("AP33333333333333333");
        monitorItem3.setUrl("http://api-pataciot-acniotsense.wise-paas.com.cn/api/v1.0/authentication/login/phone");
        monitorItem3.setRequestType(1);
        monitorItem3.setRequestBody("{\n" +
                "    \"password\":\"123456\",\n" +
                "    \"phone\":\"13900000000\",\n" +
                "    \"tokenTs\":20160\n" +
                "}\n");
        monitorItem3.setAsserts("{\n" +
                "    \"statusCode\":\"200\"\n" +
                "}");

        srp.getMonitorItems().add(monitorItem3);
        srp.getMonitorItems().add(monitorItem2);
        monitorItem3.setSrp(srp);
        monitorItem2.setSrp(srp);

        srpRepository.save(srp);
        monitorItemRepository.save(monitorItem3);
        monitorItemRepository.save(monitorItem2);
    }

    //给SRP减监控项
    @Test
    public void testItemSub() {
        MonitorItem monitorItem3 = monitorItemRepository.findByMonitorId(9L);
//        SRP srpsub = srpRepository.findBySrpId(16L);


//        boolean remove = srpsub.getMonitorItems().remove(monitorItem3);

//        monitorItemRepository.saveAndFlush(monitorItem3);
        monitorItemRepository.delete(monitorItem3);
//        System.err.println(remove);
    }

    //给SRP清空监控项
    @Test
    public void testItemClear() {
        SRP srp = srpRepository.findBySrpId(16L);

        srp.getMonitorItems().clear();
        srpRepository.saveAndFlush(srp);

        for (MonitorItem monitorItem : srp.getMonitorItems()){
            monitorItemRepository.delete(monitorItem);
        }
    }

    //SRP监控项列表
    @Test
    public void testMonitorListByclassify(){
        List<MonitorItem> bySrpIdOrderByClassify = monitorItemRepository.findBySrpIdOrderByClassify(1L);
        for (MonitorItem monitorItem : bySrpIdOrderByClassify){
            System.err.println(monitorItem);
        }

    }

    //有问题
    //删除一个srp（关系表和srp表会更新，关联user不会被删除）
    @Test
    public void testDelSrp() {

//        User user = userRepository.findByUserId(10000036L);
//        User user1 = userRepository.findByUserId(10000041L);
        SRP srp = srpRepository.findBySrpId(25L);
//
//        user.getSrps().remove(srp);
//        user1.getSrps().remove(srp);

//        user.getSrps().clear();
//        user1.getSrps().clear();
//        srp.getUsers().clear();
//
//        srp.getMonitorItems().clear();

//        userRepository.save(user);
//        userRepository.save(user1);


        srpRepository.save(srp);

        srpRepository.deleteById(25L);
    }

    @Test
    public void testDelSrp2() {

        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();

            User user = userRepository.findByUserId(10000023L);
            User user1 = userRepository.findByUserId(10000036L);
            SRP srp = srpRepository.findBySrpId(16L);

            user.getSrps().remove(srp);
            user1.getSrps().remove(srp);

            userRepository.saveAndFlush(user);
            userRepository.saveAndFlush(user1);

            srpRepository.delete(srp);

            tx.commit();
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (em != null) {

                em.close();

            }

        }
    }

//    @Transactional
    @Test
    public void testDelSrp3() {
        User user = userRepository.findByUserId(10000036L);
        SRP srp = srpRepository.findBySrpId(16L);

        user.getSrps().remove(srp);
//        user.getSrps().clear();

        userRepository.saveAndFlush(user);
        srpRepository.delete(srp);
    }

    @Test
    public void testDelSrp4() {
        //        SRP srp = srpRepository.findBySrpId(48L);
        User u1 = userRepository.findByUserId(10000054L);
        SRP srp = u1.getSrps().iterator().next();

        u1.getSrps().remove(srp);
        srp.getUsers().clear();

        srpRepository.saveAndFlush(srp);
        userRepository.saveAndFlush(u1);

        srpRepository.delete(srp);
    }

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
            user.setPhone((long) (183000000+i));
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
//        user.setUserId(10000036L);

//        userRepository.save(user);
//        userRepository.flush();
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

//    @Test
//    public void updateUser(){
//        User user = new User();
//        user.setEmail("xxxxxx");
//        user.setNickName("xx1");
//        user.setRemark("xxxxx");
//
//        userRepository.saveAndFlush(user);
//        System.err.println(user);
//    }

}
