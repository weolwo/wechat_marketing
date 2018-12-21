package com.wechat.marketing.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 购物车实体数据模型
 */
@Data
public class CartDTO implements Serializable {

    private String productId;//商品id

    private Integer productQuantity;//商品数量

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public CartDTO() {
    }
}
