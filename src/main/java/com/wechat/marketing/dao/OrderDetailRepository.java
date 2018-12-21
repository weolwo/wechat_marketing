package com.wechat.marketing.dao;

import com.wechat.marketing.pojo.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    /**
     * 根据订单id查询订单详情
     * @param orderId 订单id
     * @return
     */
    public List<OrderDetail> findByOrderId(String orderId);
}
