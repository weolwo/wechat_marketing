package com.wechat.marketing.service.impl;

import com.wechat.marketing.dto.OrderDTO;
import com.wechat.marketing.pojo.OrderDetail;
import com.wechat.marketing.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private String openid = "erdhdhd664";

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("美猫仔");
        orderDTO.setBuyerAddress("宝安");
        orderDTO.setBuyerOpenid(openid);
        orderDTO.setBuyerPhone("13800098090");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductQuantity(2);
        orderDetail.setProductId("665666232");
        orderDetailList.add(orderDetail);

        orderDTO.setOrderDetailList(orderDetailList);
        orderService.create(orderDTO);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne("153827751731");
        System.out.println(orderDTO);
    }

    @Test
    public void findList() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<OrderDTO> list = orderService.findList(openid, pageable);
        System.out.println(list.getSize());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne("1538278544540]");
        OrderDTO dto = orderService.finish(orderDTO);
        System.out.println(dto);
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne("1538278751731");
        OrderDTO dto = orderService.cancel(orderDTO);
        System.out.println(dto);
    }

    @Test
    public void pay() {
        OrderDTO orderDTO = orderService.findOne("1538278751731");
        OrderDTO dto = orderService.pay(orderDTO);
        System.out.println(dto);
    }

    @Test
    public void findList1() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<OrderDTO> list = orderService.findList(pageable);
        System.out.println(list);
    }

    @Test
    public void findOne1() {
    }
}