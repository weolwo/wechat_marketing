package com.wechat.marketing.service;

import com.wechat.marketing.dto.OrderDTO;
import com.wechat.marketing.pojo.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 订单业务层接口
 */
public interface OrderService {

    /**
     * 创建订单
     * @return
     */
    public OrderDTO create(OrderDTO orderDTO);

    /**
     * 查询某个订单
     * @param orderId 订单id
     * @return
     */
    public OrderDTO findOne(String orderId);


    /**
     * 根据openid分页查询订单列表
     * @param buyerOpenId
     * @param pageable
     * @return
     */
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable);


    /**
     * 分页查询订单列表
     * @param pageable
     * @return
     */
    public Page<OrderDTO> findList(Pageable pageable);

    /**
     * 完成订单
     * @param orderDTO
     * @return
     */
    public OrderDTO finish(OrderDTO orderDTO);

    /**
     * 取消订单
     * @param orderDTO
     * @return
     */
    public OrderDTO cancel(OrderDTO orderDTO);

    /**
     * 支付订单
     * @param orderDTO
     * @return
     */
    public OrderDTO pay(OrderDTO orderDTO);
}
