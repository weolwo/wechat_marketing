package com.wechat.marketing.controller;

import com.wechat.marketing.dto.OrderDTO;
import com.wechat.marketing.enums.ResultEnum;
import com.wechat.marketing.exception.SellException;
import com.wechat.marketing.service.OrderService;
import com.wechat.marketing.service.PurchaserService;
import com.wechat.marketing.utils.Convertor;
import com.wechat.marketing.utils.Result;
import com.wechat.marketing.validation.OrderForm;
import com.wechat.marketing.view.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买家订单控制层
 */
@RestController
@RequestMapping("buyer/order")
@Slf4j
public class PurchaserOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PurchaserService purchaserService;

    @RequestMapping("/create")
    public ResultVo<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {//如果表单验证出错
            log.error("【创建订单】错误 orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = Convertor.OrderFormToOrderDTO(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        return Result.success(map);
    }

    @RequestMapping("/list")
    public ResultVo<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【订单列表】 openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageable);
        return Result.success(orderDTOPage.getContent());
    }

    @RequestMapping("/detail")
    public ResultVo<List<OrderDTO>> findOrderDetail(@RequestParam("openid") String openid,
                                                    @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = purchaserService.findOneOrder(openid,orderId);

        return Result.success(orderDTO);
    }

    @RequestMapping("/cancel")
    public ResultVo<List<OrderDTO>> cancelOrder(@RequestParam("openid") String openid,
                                                    @RequestParam("orderId") String orderId) {
        purchaserService.cancelOrder(openid,orderId);

        return Result.success();
    }
}

