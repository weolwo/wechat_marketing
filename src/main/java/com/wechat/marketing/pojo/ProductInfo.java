package com.wechat.marketing.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wechat.marketing.enums.ProductStatusEnum;
import com.wechat.marketing.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class ProductInfo implements Serializable {

    @Id
    private String productId;//商品id

    private String productName;//商品名称

    private BigDecimal productPrice;//商品价格

    private Integer productStoct;//库存

    private String productDescription;//商品描述

    private String productIcon;//商品图片

    private Integer categoryType;//类目编号

    private Integer productStatus=ProductStatusEnum.DOWN.getCode();//商品状态,0上架,1下架

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){

      return   EnumUtil.getByCode(productStatus,ProductStatusEnum.class);
    }

}
