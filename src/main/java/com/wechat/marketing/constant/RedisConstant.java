package com.wechat.marketing.constant;

/**
 * redis相关的常量
 */
public interface RedisConstant {

    //过期时间
    Integer EXPIRE=7200;//2小时

    //key的前缀
    String TOKEN_PREFIX="token_%s";

}
