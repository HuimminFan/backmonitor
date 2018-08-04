package com.fhm.backmonitor.enums;

/**
 * 返回异常结果的枚举
 */
public enum ResultEnum {

    LOGIN_ERROR("403","登陆错误，请登陆!");

    /**状态码*/
    private String code;
    /**错误信息*/
    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
