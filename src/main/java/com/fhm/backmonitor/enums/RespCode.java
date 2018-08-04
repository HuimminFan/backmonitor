package com.fhm.backmonitor.enums;

/**
 * Created by wuao.tp on 2018/7/20.
 */
public enum RespCode {

    SUCCESS(0, "请求成功"),
    WARN(-1, "网络异常，请稍后重试");
    private int code;
    private String msg;

    RespCode(int code, String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
