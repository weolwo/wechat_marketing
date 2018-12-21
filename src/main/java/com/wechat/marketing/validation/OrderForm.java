package com.wechat.marketing.validation;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderForm {

    @NotEmpty(message = "姓名不能为空")
    private String name;//买家姓名

    @NotEmpty(message = "电话不能为空")
    private String phone;//买家电话

    @NotEmpty(message = "地址不能为空")
    private String address;//买家地址

    @NotEmpty(message = "微信openId不能为空")
    private String openid;//买家微信openID

    @NotEmpty(message = "购物车不能为空")
    private String items;//买家购物车

}
