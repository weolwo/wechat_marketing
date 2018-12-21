package com.wechat.marketing.utils;

import com.wechat.marketing.view.ResultVo;
import lombok.Data;

/**
 * 封装最终返回的商品信息结果集
 */
public class Result {

    /**
     * 成功时返回的结果集
     * @param object //封装页面需要的显示的对象数据
     * @return
     */
   public static ResultVo success(Object object){
       ResultVo resultVo=new ResultVo();
       resultVo.setCode(0);
       resultVo.setMsg("成功");
       resultVo.setData(object);

       return resultVo;
   }


    //当返回结果为空时
    public static ResultVo success(){

        return success(null);
    }


    /**
     * 当发生错误的时候返回
     * @param code 错误码
     * @param message 提示信息
     * @return
     */
    public static ResultVo error(Integer code ,String message){
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(code);
        resultVo.setMsg(message);

        return resultVo;
    }
}
