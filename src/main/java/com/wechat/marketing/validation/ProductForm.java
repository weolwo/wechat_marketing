package com.wechat.marketing.validation;

import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;

@Data
public class ProductForm {

    private String productId;//商品id

    private String productName;//商品名称

    private BigDecimal productPrice;//商品价格

    private Integer productStoct;//库存

    private String productDescription;//商品描述

    private String productIcon;//商品图片

    private Integer categoryType;//类目编号
}
