package com.fhm.backmonitor.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fhm.backmonitor.entity.MonitorItem;
import com.fhm.backmonitor.service.MonitorItemService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fhm on 2018/8/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class testMonitor {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    MonitorItemService monitorItemService;

    //tooken
    public static String  token;

    //accessToken
    public static String  accessToken;

    //处理页面和接口类型（RestTemplete）
    //取结果与断言比对，最后结果为code，成功还是失败
    public Integer testPageAndApi(MonitorItem monitorItem){
        String url=monitorItem.getUrl();
        Integer requestType=monitorItem.getRequestType();
        String asserts = monitorItem.getAsserts();

        int code = 1;   //0为失败，1为成功

        HttpHeaders requestHeaders = new HttpHeaders();

        String requestBodyStr = monitorItem.getRequestBody();

        Map requestBody = new HashMap();

        //POST型，登录生成token
        if(requestType==1){
            requestBody = (Map) JSON.parse(requestBodyStr);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(requestBody, requestHeaders);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            JSONObject resJsonObject = JSON.parseObject(responseEntity.getBody());//接口返回内容的json对象

            int statusCodeValue = responseEntity.getStatusCodeValue();
            if (statusCodeValue!=200){
                //程序终止，退出
                System.err.println("退出");
            }

            JSONArray assertsArraysJsonObject = JSON.parseArray(asserts);//传来的断言的json对象数组

            //断言数组循环判断,与监控项返回的JSON对象比对
            for ( Object assJsonObject : assertsArraysJsonObject){
                JSONObject assJsonObject1 = (JSONObject) assJsonObject;

                //status为0是等号，为1是不等号
                if(assJsonObject1.get("ststus").equals("0")){
                    if(resJsonObject.get(assJsonObject1.get("akey")).equals(assJsonObject1.get("value"))){
                        code=(code & 1);//成功
                    }
                    else {
                        code=(code & 0);//失败
                    }
                }
                else{
                    if(!resJsonObject.get(assJsonObject1.get("akey")).equals(assJsonObject1.get("value")))
                        code=(code & 1);    //所有断言判断都成功这个监控项才算成功，有一个失败就算失败
                    else{
                        code &= 0;
                    }
                    if (monitorItem.getClassify()==1){
                        accessToken = (String) resJsonObject.get(assJsonObject1.get("akey"));
                    }
                    else if (monitorItem.getClassify()==3){
                        token = (String) resJsonObject.get(assJsonObject1.get("akey"));
                    }
                }
            }
        }
        //GET请求，携带token访问
        else if (requestType==2){
            if (monitorItem.getClassify()==2){
                requestHeaders.add("Authorization","Bearer "+accessToken);
            }
            else if (monitorItem.getClassify()==4){
                requestHeaders.add("Authorization","Bearer "+token);
            }

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(requestBody, requestHeaders);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            JSONObject resJsonObject = JSON.parseObject(responseEntity.getBody());//接口返回内容的json对象

            JSONArray assertsArraysJsonObject = JSON.parseArray(asserts);//传来的断言的json对象数组

            //断言数组循环判断
            for ( Object assJsonObject : assertsArraysJsonObject){
                JSONObject assJsonObject1 = (JSONObject) assJsonObject;
                if(assJsonObject1.get("ststus").equals("0")){
                    //接口返回的对应asserts中value的值
                    String revalue = resJsonObject.get(assJsonObject1.get("akey")).toString();
                    if(revalue.equals(assJsonObject1.get("value")))
                        code=(code & 1);//成功
                    else{
                        code=(code & 0);//失败
                    }
                }
                else {
                    if(!resJsonObject.get(assJsonObject1.get("akey")).equals(assJsonObject1.get("value")))
                        code=(code & 1);    //所有断言判断都成功这个监控项才算成功，有一个失败就算失败
                    else{
                        code=(code & 0);
                    }
                }
            }
        }
        System.err.println(monitorItem.getMonitorName()+":"+code);
        return code;
    }

    //处理视频类型，最后结果是code，成功还是失败
    public void testVideo(){
        //1、测试视频相关所有接口
        //2、测试视频文件获取


    }

    @Test
    public void monitorLogic(){
        System.err.println("start1");
        //SRP的所有监控项（sort by classify）

        List<MonitorItem> monitorItems = monitorItemService.getMonitTtemListBySrpId(66L);

        for (MonitorItem monitorItem:monitorItems){
            //平台登录
            if (monitorItem.getClassify()==1){

                System.out.println(testPageAndApi(monitorItem));
            }
            //平台接口
            else if (monitorItem.getClassify()==2){
                //视频类型的监控项
                if (monitorItem.getMonitorType()==2){
                    //testVideo
                    System.out.println(monitorItem.getMonitorName()+":"+"不知道");

                }
                //页面或接口类型
                else{
                    System.out.println(testPageAndApi(monitorItem));
                }
            }

            //SRP登录
            if (monitorItem.getClassify()==3){
                testPageAndApi(monitorItem);
            }
            //SRP接口
            else if (monitorItem.getClassify()==4){
                //视频类型的监控项
                if (monitorItem.getMonitorType()==2){
                    //testVideo
                    System.out.println(monitorItem.getMonitorName()+":"+"不知道");

                }
                //页面或接口类型
                else{
                    System.out.println(testPageAndApi(monitorItem));
                }
            }
        }

    }
}
