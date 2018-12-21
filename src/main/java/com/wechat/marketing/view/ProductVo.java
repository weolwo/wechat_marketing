package com.wechat.marketing.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品类目信息
 *
 */
@Data
public class ProductVo{

    @JsonProperty("name")
    private String categoryName; //类目名称

    @JsonProperty("type")
    private Integer categorType;//类目编号

    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoList;//商品详情列表数据
}
