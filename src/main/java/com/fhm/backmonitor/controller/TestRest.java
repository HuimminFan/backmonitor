package com.fhm.backmonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fhm on 2018/7/20.
 */
@Controller
@RequestMapping(value="/test")
public class TestRest {
    //调用spring里面RestTemplate需要配置bean
    @Autowired
    private RestTemplate restTemplate;

    /**
     * post请求远程调用

     * @param usernmae
     * @param password
     * @throws Exception
     */
    @RequestMapping(value="/SSO", method= RequestMethod.POST)
    public void  testSSO(@PathVariable String usernmae, @PathVariable String password) throws Exception{
        String url="http://portal-sso.wise-paas.com.cn/v1.3/auth/native";
        HttpHeaders headers = new HttpHeaders();
        //定义请求参数类型，这里用json所以是MediaType.APPLICATION_JSON
        headers.setContentType(MediaType.APPLICATION_JSON);
        //RestTemplate带参传的时候要用HttpEntity<?>对象传递
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("usernmae", usernmae);
        map.put("password", password);

        HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(map, headers);

        ResponseEntity<String> entity = restTemplate.postForEntity(url, request, String.class);
        //获取3方接口返回的数据通过entity.getBody();它返回的是一个字符串；
        String body = entity.getBody();
        HttpStatus code = entity.getStatusCode();
        HttpHeaders httpHeaders = entity.getHeaders();

//        System.out.println(body);
//        System.out.println(code);
//        System.out.println(httpHeaders);
//        JSONObject json;
//        json = JSONObject.parseObject(str);
        //然后把str转换成JSON再通过getJSONObject()方法获取到里面的result对象，因为我想要的数据都在result里面
        //下面的strToJson只是一个str转JSON的一个共用方法；
//        JSONObject json = StringUtil.strToJson(body);
//        if(json != null){
//            JSONObject user_json = json.getJSONObject("result");
//            //这里考虑 到result也可能为null的情况，因为字符串转json会把字段为null的过滤掉；
//            if(user_json != null && !"{}".equals(user_json.toString())){
//                //调用JSONObject.toJavaObject()把JSON转成java对象最后抛出数据即可
//                User user = JSONObject.toJavaObject(user_json, User.class);
//                //最后抛出json数据
//                Json.toJson(new Result(true, 0, "获取信息成功", user), response);
//                return;
//            }else{
//                Json.toJson(new Result(false, 1, "没有信息", null), response);
//                return;
//            }
//        }else{
//            Json.toJson(new Result(false, 1, "没有信息", null), response);
//        }
    }

}
