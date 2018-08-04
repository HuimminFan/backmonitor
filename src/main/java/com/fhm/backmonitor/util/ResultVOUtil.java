package com.fhm.backmonitor.util;


import com.fhm.backmonitor.entity.ResultVo;
import com.fhm.backmonitor.enums.ResultEnum;

/**
 * @author wqh
 * @Date 2017/10/30 21:46
 * @Description:
 */
public  class ResultVOUtil {

    public static ResultVo success(Object object){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode("200");
        resultVo.setMsg("succ");
        resultVo.setData(object);
        return resultVo;
    }

    public static ResultVo success(){
        return ResultVOUtil.success(null);
    }

    public static ResultVo error(String code,String msg){
        return  new ResultVo(code,msg);
    }
    public static ResultVo error(ResultEnum resultEnum){
        return  new ResultVo(resultEnum);
    }


}
