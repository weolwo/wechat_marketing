package com.wechat.marketing.service;

import com.wechat.marketing.pojo.SellerInfo;

public interface SellerService {

    /**
     * 根据openid查询登录信息
     * @param openid
     * @return
     */
    public SellerInfo findByOpenid(String openid);
}
