package com.wechat.marketing.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wechat.marketing.enums.OrderStatusEnum;
import com.wechat.marketing.enums.PayStatusEnum;
import com.wechat.marketing.pojo.OrderDetail;
import com.wechat.marketing.utils.DateToLongSerialize;
import com.wechat.marketing.utils.EnumUtil;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用于接收前端传过来的参数
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)//如果当对象返回值为null时，在返回前端时及那个会不显示
public class OrderDTO {

    /**
     * 订单id.
     */
    @Id
    private String orderId;

    /**
     * 买家名字.
     */
    private String buyerName;

    /**
     * 买家手机号.
     */
    private String buyerPhone;

    /**
     * 买家地址.
     */
    private String buyerAddress;

    /**
     * 买家微信Openid.
     */
    private String buyerOpenid;

    /**
     * 订单总金额.
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态, 默认为0新下单.
     */
    private Integer orderStatus;

    /**
     * 支付状态, 默认为0未支付.
     */
    private Integer payStatus;

    /**
     * 创建时间.
     */
    @JsonSerialize(using = DateToLongSerialize.class)
    private Date createTime;

    /**
     * 更新时间.
     */
    @JsonSerialize(using = DateToLongSerialize.class)//序列化成我们需要的时间格式
    private Date updateTime;

    private List<OrderDetail> orderDetailList;


    @JsonIgnore//当对象转json格式时,该方法就会被忽虑掉
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore//当对象转json格式时,该方法就会被忽虑掉
    public PayStatusEnum getOrderPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
