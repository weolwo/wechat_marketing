package com.wechat.marketing.utils;

import com.alibaba.fastjson.JSON;
import com.wechat.marketing.dto.OrderDTO;
import com.wechat.marketing.exception.SellException;
import com.wechat.marketing.pojo.OrderDetail;
import com.wechat.marketing.pojo.OrderMaster;
import com.wechat.marketing.validation.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 转换器
 */
@Slf4j
public class Convertor {

    public static OrderDTO OrderMasterConvertOrderDTO(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }


    public static List<OrderDTO> OrderMasterListConvertOrderDTOList(List<OrderMaster> orderMasterList) {
  /*    List<OrderDTO> orderDTOList=new ArrayList<>();
        OrderDTO orderDTO=new OrderDTO();
        for (OrderMaster orderMaster : orderMasterList) {
            BeanUtils.copyProperties(orderMaster,orderDTO);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;*/
        return orderMasterList.stream().map(e -> OrderMasterConvertOrderDTO(e)).collect(Collectors.toList());
    }

    public static OrderDTO OrderFormToOrderDTO(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        //把字符串转换成list
        List<OrderDetail> orderDetailList = null;
        try {
            orderDetailList = JSON.parseArray(orderForm.getItems(), OrderDetail.class);
        } catch (Exception e) {
            log.error("【对象转换】异常 strint={}",orderForm.getItems());
            throw new SellException(e.getMessage());
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

}
