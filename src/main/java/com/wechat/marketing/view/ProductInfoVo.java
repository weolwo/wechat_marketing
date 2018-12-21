package com.wechat.marketing.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详情
 */
@Data
public class ProductInfoVo {

    @JsonProperty("id")
    private String productId;//商品id

    @JsonProperty("name")
    private String productName;//商品名称

    @JsonProperty("price")
    private BigDecimal productPrice;//价格

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;//图片路径
}
