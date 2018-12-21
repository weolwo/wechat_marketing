package com.wechat.marketing.utils;

import com.wechat.marketing.enums.CodeEnum;

/**
 * 用于获取某个枚举类的工具类
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {

        for (T each : enumClass.getEnumConstants()) {

            if (code.equals(each.getCode())) {

                return each;
            }
        }
        return null;
    }
}
