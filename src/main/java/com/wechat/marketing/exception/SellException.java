package com.wechat.marketing.exception;

import com.wechat.marketing.enums.ResultEnum;
import lombok.Getter;

/**
 * 跟商品相关的全局异常处理类
 */
@Getter
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum) {//传入枚举类

        super(resultEnum.getMessage());//把message信息传到父类的构造方法中

        this.code=resultEnum.getCode();
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public SellException(String message) {
        super(message);
    }
}

