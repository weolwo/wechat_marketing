package com.wechat.marketing.dao;

import com.wechat.marketing.pojo.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void save(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetailId("8666866");
        orderDetail.setOrderId("1111111111");
        orderDetail.setProductIcon("http:\\xxxxx.jpg");
        orderDetail.setProductId("6622");
        orderDetail.setProductName("奶茶");
        orderDetail.setProductPrice(new BigDecimal(6.5));
        orderDetail.setProductQuantity(2);
        repository.save(orderDetail);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList = repository.findByOrderId("123456733");
        System.out.println(orderDetailList.size());
    }
}