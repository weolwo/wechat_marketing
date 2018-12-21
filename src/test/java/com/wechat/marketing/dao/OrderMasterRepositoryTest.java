package com.wechat.marketing.dao;

import com.wechat.marketing.pojo.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void findByBuyerOpenid() {
        Pageable pageable=PageRequest.of(0,2);
        Page<OrderMaster> page = repository.findByBuyerOpenid("44344334", pageable);
        System.out.println(page.getTotalElements());
    }

    @Test
    public void save(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("1234567339");
        orderMaster.setBuyerName("白猫");
        orderMaster.setBuyerAddress("深圳");
        orderMaster.setBuyerOpenid("44344334");
        orderMaster.setBuyerPhone("13690908989");
        orderMaster.setOrderAmount(new BigDecimal(27.5));

        repository.save(orderMaster);
    }
}