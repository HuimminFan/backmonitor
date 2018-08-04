package com.fhm.backmonitor.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fhm.backmonitor.entity.MonitorItem;
import com.fhm.backmonitor.repository.SrpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fhm on 2018/7/26.
 * 1、SSO登录监控。POST，取参数，RestTemplete，判断连接，取状态码判断是否200，
 *      取tooken判断是否为null，是就往下继续走判断接口，否就返回
 * 2、SSO下接口
 */
public class MonitorService {

    @Autowired
    private static RestTemplate restTemplate;
    private static MonitorItemService monitorItemService;
    private static SrpRepository srpRepository;


    //tooken
    public static String  token;

    //处理页面和接口类型（RestTemplete）
    //取结果与断言比对，最后结果为code，成功还是失败
    public static Integer testPageAndApi(MonitorItem monitorItem){
        String url=monitorItem.getUrl();
        Integer requestType=monitorItem.getRequestType();
        String asserts = monitorItem.getAsserts();

        int code = 0;   //成功还是失败

        HttpHeaders requestHeaders = new HttpHeaders();

        String requestBodyStr = monitorItem.getRequestBody();

//      String str = "{\"0\":\"zhangsan\",\"1\":\"lisi\",\"2\":\"wangwu\",\"3\":\"maliu\"}";
        Map requestBody = new HashMap();
        //POST型，登录生成token
        if(requestType==1){
            requestBody = (Map) JSON.parse(requestBodyStr);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(requestBody, requestHeaders);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            //写死，不传asserts，直接判断返回结果的状态码是否200，token是否为null
//            int statusCodeValue = responseEntity.getStatusCodeValue();
//            JSONObject parse = JSON.parseObject(responseEntity.getBody());
//            token = parse.get("accessToken").toString();    //tooken赋值
//            if (statusCodeValue==200&&token!=null)
//                code=0;
//            code=1;

            JSONObject resJsonObject = JSON.parseObject(responseEntity.getBody());//接口返回内容的json对象
            JSONArray assertsArraysJsonObject = JSON.parseArray(asserts);//传来的断言的json对象数组

            //断言数组循环判断,与监控项返回的JSON对象比对
            for ( Object assJsonObject : assertsArraysJsonObject){
                JSONObject assJsonObject1 = (JSONObject) assJsonObject;

                if(assJsonObject1.get("ststus")=="0"){
                    if(resJsonObject.get(assJsonObject1.get("key"))==assJsonObject1.get("value"))
                        code=(code & 0);//成功
                    code=(code & 1);//失败
                }
                else if(assJsonObject1.get("status")=="1"){
                    if(resJsonObject.get(assJsonObject1.get("key"))!=assJsonObject1.get("value"))
                        code=(code & 0);    //所有断言判断都成功这个监控项才算成功，有一个失败就算失败
                    code &= 1;
                }
            }
        }
        //GET请求，携带token访问
        else if (requestType==2){
            requestHeaders.add("Authorization","Bearer "+token);
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(requestBody, requestHeaders);

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            //int statusCodeValue = responseEntity.getStatusCodeValue();
            JSONObject resJsonObject = JSON.parseObject(responseEntity.getBody());//接口返回内容的json对象

            JSONArray assertsArraysJsonObject = JSON.parseArray(asserts);//传来的断言的json对象数组

            //断言数组循环判断
            for ( Object assJsonObject : assertsArraysJsonObject){
                JSONObject assJsonObject1 = (JSONObject) assJsonObject;

                if(assJsonObject1.get("ststus")=="0"){
                    if(resJsonObject.get(assJsonObject1.get("key"))==assJsonObject1.get("value"))
                        code=(code & 0);//成功
                    code=(code & 1);//失败
                }
                else if(assJsonObject1.get("status")=="1"){
                    if(resJsonObject.get(assJsonObject1.get("key"))!=assJsonObject1.get("value"))
                        code=(code & 0);    //所有断言判断都成功这个监控项才算成功，有一个失败就算失败
                    code=(code & 1);
                }
            }
        }
        return code;
    }

    //处理视频类型，最后结果是code，成功还是失败
    public void testVideo(){
        //1、测试视频相关所有接口
        //2、测试视频文件获取


    }

    public static void monitorLogic(Long srpId){
        //SRP的所有监控项（sort by classify）
        List<MonitorItem> monitorItems = monitorItemService.getMonitTtemListBySrpId(srpId);

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
                    System.out.println("不知道");

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
                    System.out.println("不知道");

                }
                //页面或接口类型
                else{
                    System.out.println(testPageAndApi(monitorItem));
                }
            }
        }

    }

    public static void main(String[] args){

        monitorLogic(1L);

    }
}
