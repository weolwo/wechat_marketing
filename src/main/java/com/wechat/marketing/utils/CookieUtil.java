package com.wechat.marketing.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作cookie存取的工具类
 */
public class CookieUtil {

    /**
     * @param response
     * @param name     名称
     * @param value    值
     * @param maxAge   过期时间
     */
    public static void setCookie(HttpServletResponse response, String name, String value, Integer maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     *
     * @param cookieName
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Map<String, Cookie> map = readCookie(request);
        if (map.containsKey(cookieName)) {
            return map.get(cookieName);
        } else {
            return null;
        }
    }

    /**
     * 将Cookie封装成map
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookie(HttpServletRequest request) {
        Map cookieMap = new HashMap();
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
