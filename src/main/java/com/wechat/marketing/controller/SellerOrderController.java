package com.wechat.marketing.controller;

import com.wechat.marketing.dto.OrderDTO;
import com.wechat.marketing.enums.ResultEnum;
import com.wechat.marketing.exception.SellException;
import com.wechat.marketing.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家控制层
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 商家查询订单列表
     *
     * @param page 当前页码
     * @param size 每页显示多少页
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageable);
        map.put("orderList", orderDTOPage.getContent());
        map.put("totalPage", orderDTOPage.getTotalPages());
        map.put("size", size);
        map.put("curPage", page);
        return new ModelAndView("order/list", map);
    }

    /**
     * 商家取消订单
     *
     * @param orderId 订单id
     * @param map
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId, Map<String, Object> map) {

        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("[商家取消订单] 未查询到订单 e={}", e);
            map.put("msg", e.getMessage());
            map.put("url", "http://localhost:8080/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "http://localhost:8080/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 商家取查询订单详情
     *
     * @param orderId 订单id
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (SellException e) {
            log.error("[商家查询订单] 未查询到订单 e={}", e);
            map.put("msg", e.getMessage());
            map.put("url", "http://localhost:8080/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }
    /**
     * 商家完结订单
     *
     * @param orderId 订单id
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        try {
            OrderDTO  orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (SellException e) {
            log.error("[商家完结订单] 完结订单发生异常 e={}", e);
            map.put("msg", e.getMessage());
            map.put("url", "http://localhost:8080/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url", "http://localhost:8080/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}
