package com.wechat.marketing.dao;

import com.wechat.marketing.pojo.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    /**
     * 根据买家openId查询订单
     * @param buyerOpenId 买家openId
     * @return List<OrderMaster>
     */
    public Page<OrderMaster> findByBuyerOpenid(String buyerOpenId, Pageable pageable);

    /**
     * 查询某个订单
     * @param orderId
     * @return
     */
    public OrderMaster findByOrderId(String orderId);

}
