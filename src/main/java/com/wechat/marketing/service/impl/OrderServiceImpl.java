package com.wechat.marketing.service.impl;

import com.wechat.marketing.dao.OrderDetailRepository;
import com.wechat.marketing.dao.OrderMasterRepository;
import com.wechat.marketing.dto.CartDTO;
import com.wechat.marketing.dto.OrderDTO;
import com.wechat.marketing.enums.OrderStatusEnum;
import com.wechat.marketing.enums.PayStatusEnum;
import com.wechat.marketing.enums.ResultEnum;
import com.wechat.marketing.exception.BankException;
import com.wechat.marketing.exception.SellException;
import com.wechat.marketing.pojo.OrderDetail;
import com.wechat.marketing.pojo.OrderMaster;
import com.wechat.marketing.pojo.ProductInfo;
import com.wechat.marketing.service.OrderService;
import com.wechat.marketing.service.ProductInfoService;
import com.wechat.marketing.utils.Convertor;
import com.wechat.marketing.utils.UUIDUtil;
import com.wechat.marketing.websocket.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private WebSocketService webSocketService;

    BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);//订单金额

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        //生成订单id
        String orderId = UUIDUtil.generateUUID();
        //1.查询商品(数量和id)
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findById(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
               // throw new BankException();//测试响应固定的HTTP状态码
            }
            //2.计算订单总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            //保存订单明细
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(UUIDUtil.generateUUID());
            //由于前端只给我们传了数量和商品id,其他属性还需要我们自己设置
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        //3.保存订单到数据库
        OrderMaster order = new OrderMaster();
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        //把OrderDTO对象中的属性复制到ordermaster对象中
        BeanUtils.copyProperties(orderDTO, order);
        orderMasterRepository.save(order);
        //4.扣减库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
       //5. 使用websocket技术向商家推送有新订单的消息
        webSocketService.sendMessage(orderDTO.getOrderId());
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findByOrderId(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //查询订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenId, pageable);
        List<OrderDTO> orderDTOList = Convertor.OrderMasterListConvertOrderDTOList(orderMasterPage.getContent());
        //把orderMasterPage转换成OrderDTO并返回
        return new PageImpl(orderDTOList, pageable, orderMasterPage.getTotalElements());

    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //1.判断订单是否可以取消
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.info("【取消订单】 订单状态不正确,orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.info("【更新订单】更新订单失败 orderMaster", orderMaster);

            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //3.修改库存，返还库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.info("【取消订单】订单中无商品详情,ordeerDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);
        //4.退款,如果已支付，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS)) {
            System.out.println("退款成功");
            //TODO
        }

        //5.向用户推送取消成功的消息
        //TODO
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.info("【完结订单】 订单状态不正确,orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.info("【完结订单】更新订单失败 orderMaster", orderMaster);

            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //TODO,向用户推送订单完结的消息
        return orderDTO;
    }

    @Override
    public OrderDTO pay(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.info("【订单支付】 订单支付状态不正确,orderId={},payStatus={}", orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.info("【订单支付】更新订单支付状态失败 orderMaster", orderMaster);

            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {

        Page<OrderMaster> page = orderMasterRepository.findAll(pageable);

        List<OrderDTO> orderDTOList = Convertor.OrderMasterListConvertOrderDTOList(page.getContent());

        return new PageImpl(orderDTOList, pageable, page.getTotalElements());
    }
}
