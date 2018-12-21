package com.wechat.marketing.aspect;

import com.wechat.marketing.constant.CookieConstant;
import com.wechat.marketing.constant.RedisConstant;
import com.wechat.marketing.exception.SellerAuthorizeException;
import com.wechat.marketing.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 商家用户访问授权切面类
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //校验
    //切入点
    @Pointcut("execution(public * com.wechat.marketing.controller.Seller*.*(..))" +//拦截以Seller开头的所以类
            "&& ! execution(public * com.wechat.marketing.controller.SellerInfoController.*(..))")//排除登录和注销的方法
    public void verify() {
    }

    @Before("verify()")
    public void doVerify() {
        //获取request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取cooki信息
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("[登录校验] cookie中查询不到token");
            throw new SellerAuthorizeException();
        }
        //到redis中查询token
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("[登录校验] redis中查询不到token");
            throw new SellerAuthorizeException();
        }
    }
}
