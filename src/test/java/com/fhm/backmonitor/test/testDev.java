package com.fhm.backmonitor.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import top.watech.backmonitor.entity.User;
import top.watech.backmonitor.repository.UserRepository;
import top.watech.backmonitor.service.MonitorItemService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fhm on 2018/8/7.
 * 先通过接口 /device/group 取得设备分组信息
 * 再遍历组id，将组id拼接到接口 /device/detail 上
 * 就取到了每个组里的所有设备
 * 取每个设备的linked，全为false则接口挂了
 * 个别设备为false则导出出错信息
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class testDev {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    MonitorItemService monitorItemService;
    @Autowired
    UserRepository userRepository;

    //tooken
    public static String  token;

    public String testToken(Long userId){
        String url ="http://api-pataciot-acniotsense.wise-paas.com.cn/api/v1.0/authentication/login/phone";
        HttpHeaders requestHeaders = new HttpHeaders();
        Map<String, Object> requestBody = new HashMap<String, Object>();
        User user = userRepository.findByUserId(userId);
        requestBody.put("password", user.getUserPwd());
        requestBody.put("phone",user.getPhone());
        requestBody.put("tokenTs", 20160);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(requestBody, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST,requestEntity,String.class);

        JSONObject resJsonObject = JSON.parseObject(responseEntity.getBody());//接口返回内容的json对象
        token = resJsonObject.get("token").toString();
        return token;
    }

    @Test
    public void testDev(){
        String url ="http://api-pataciot-acniotsense.wise-paas.com.cn/api/v1.0/device/group";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization","Bearer "+token);

        Map<String, Object> requestBody = new HashMap<String, Object>();
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(requestBody, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET,requestEntity,String.class);
        JSONObject resJsonObject = JSON.parseObject(responseEntity.getBody());//接口返回内容的json对象
        System.err.println(resJsonObject);
        //这里取到的resJsonObject对象，先取data中的内容get("data")
        //再将data转成json数组，之后可以取出组id，组名
        //循环组id
        //将取到的id拼到 /device/detail?groupid=3&userid=1 下，生成新的URL
        //然后取新接口的结果，判断linked
    }

}
