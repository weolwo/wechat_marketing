package com.wechat.marketing.view;

import lombok.Data;

/**
 * 返回的最外层对象
 * @param <T>
 */
@Data
public class ResultVo<T> {

    private Integer code;//错误码

    private String msg;//返回信息

    private T data;//数据
}
