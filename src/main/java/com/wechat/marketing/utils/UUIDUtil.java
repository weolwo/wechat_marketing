package com.wechat.marketing.utils;

import java.util.Random;

/**
 * 生成id工具类
 */
public class UUIDUtil {

    public static String generateUUID() {

        return ((long) (Math.random() * 1000000) + System.currentTimeMillis()) + "";
    }

}
