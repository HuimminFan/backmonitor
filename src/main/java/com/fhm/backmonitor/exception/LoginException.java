package com.fhm.backmonitor.exception;


import com.fhm.backmonitor.enums.ResultEnum;

/**
 * 用户登录异常
 */
public class LoginException extends RuntimeException{
    private String code;

    public LoginException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public LoginException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
