package com.wechat.marketing.exception;

/**
 * 接收跟银行接口调用相关的异常
 * 有时后当我们程序出现异常后,http状态码,可能会要求返回特定的,据需要更改默认的状态码
 */
public class BankException extends RuntimeException {
}
