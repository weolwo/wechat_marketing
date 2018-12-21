package com.wechat.marketing.service;

import com.wechat.marketing.dto.OrderDTO;

/**
 * 买家业务层接口
 */
public interface PurchaserService {


    /**
     * 买家查询订单详情
     * @param openId 微信openid
     * @param orderId 订单id
     * @return
     */
    public OrderDTO findOneOrder(String openId,String orderId);

    /**
     * 买家取消订单
     * @param openId 微信openid
     * @param orderId 订单id
     * @return
     */
    public OrderDTO cancelOrder(String openId,String orderId);
}
