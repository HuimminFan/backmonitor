package com.fhm.backmonitor.test;

import com.fhm.backmonitor.entity.MonitorItem;
import com.fhm.backmonitor.entity.SRP;
import com.fhm.backmonitor.entity.User;
import com.fhm.backmonitor.repository.MonitorItemRepository;
import com.fhm.backmonitor.repository.SrpRepository;
import com.fhm.backmonitor.repository.UserRepository;
import com.fhm.backmonitor.service.SRPService;
import com.fhm.backmonitor.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wuao.tp on 2018/7/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class testSrp {

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

        srp1.setDescription("ddddddddddddddddddd");
        srpRepository.saveAndFlush(srp1);
    }


    //删除一个srp（关系表和srp表会更新，关联user不会被删除,关联的监控项会删掉）
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

    //给SRP减所属用户(解除关系)
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
        MonitorItem monitorItem2 = new MonitorItem();
//        MonitorItem monitorItem3 = monitorItemRepository.findByMonitorId(1L);
//        MonitorItem monitorItem4 = monitorItemRepository.findByMonitorId(3L);

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
        List<MonitorItem> monitorItemList = monitorItemRepository.findBySrpIdOrderByClassify(2L);
        for (MonitorItem monitorItem : monitorItemList){
            monitorItemRepository.delete(monitorItem);
        }
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
