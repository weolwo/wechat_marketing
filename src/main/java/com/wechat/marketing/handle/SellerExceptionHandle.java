package com.wechat.marketing.handle;

import com.wechat.marketing.exception.BankException;
import com.wechat.marketing.exception.SellException;
import com.wechat.marketing.exception.SellerAuthorizeException;
import com.wechat.marketing.utils.Result;
import com.wechat.marketing.view.ResultVo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * controller层全局异常处理器
 */
@ControllerAdvice
public class SellerExceptionHandle {

    //拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)//要处理的异常类
    public ModelAndView handleAuthorizeException() {

        //TODO,应该调用微信接口
        //跳转到登录页面
        return new ModelAndView("redirect:http://localhost:8080/sell/login.html");
    }

    /**
     * 拦截SellException相关的异常
     *
     * @return
     */
    @ExceptionHandler(value = SellException.class)
    @ResponseBody//需要返回json字符串
    public ResultVo handleSellException(SellException e) {

        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 拦截SellException相关的异常
     *
     * @return
     */
    @ExceptionHandler(value = BankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)//出现异常时客服端http状态码为403
    public void handleBankException(BankException e) {
    }
}
