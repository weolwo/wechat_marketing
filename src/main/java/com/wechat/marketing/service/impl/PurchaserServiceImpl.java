package com.wechat.marketing.service.impl;

import com.wechat.marketing.dao.OrderMasterRepository;
import com.wechat.marketing.dto.OrderDTO;
import com.wechat.marketing.enums.ResultEnum;
import com.wechat.marketing.exception.SellException;
import com.wechat.marketing.pojo.OrderMaster;
import com.wechat.marketing.service.OrderService;
import com.wechat.marketing.service.PurchaserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 买家订单业务层实现类
 */
@Service
@Slf4j
public class PurchaserServiceImpl implements PurchaserService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOneOrder(String openid, String orderId) {

        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openId, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openId, orderId);
        if (orderDTO==null){
            log.error("【取消订单】订单不存在 orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO==null){
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){

            log.error("【查询订单信息】 openid不一致 openId={}, orderDTO",openid,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
