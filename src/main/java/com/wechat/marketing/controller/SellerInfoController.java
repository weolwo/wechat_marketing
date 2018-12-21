package com.wechat.marketing.controller;

import com.wechat.marketing.constant.CookieConstant;
import com.wechat.marketing.constant.RedisConstant;
import com.wechat.marketing.enums.ResultEnum;
import com.wechat.marketing.pojo.SellerInfo;
import com.wechat.marketing.service.SellerService;
import com.wechat.marketing.utils.CookieUtil;
import com.wechat.marketing.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerInfoController {

    @Autowired
    SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 商家登录
     *
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletResponse response, @RequestParam(value = "openid",required = false) String openid, Map<String, Object> map) {
        //openid查询数据库
        SellerInfo sellerInfo = sellerService.findByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        String token = UUIDUtil.generateUUID();
        Integer expire = RedisConstant.EXPIRE;//过期时间
        //保存openid到redis
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);
        //保存openid到cookie
        CookieUtil.setCookie(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);
        return new ModelAndView("redirect:http://localhost:8080/sell/seller/order/list");
    }


    /**
     * 商家退出登录
     *
     * @return
     */
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        //获取cookie信息
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //清楚redis中的cookie
            //2.通过token查询redis，删除token为key的记录
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            //清楚本地cookie
            CookieUtil.setCookie(response, cookie.getName(), null, 0);
        }
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "http://localhost:8080/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}
