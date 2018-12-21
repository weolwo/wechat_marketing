package com.wechat.marketing.dao;

import com.wechat.marketing.pojo.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品持久层接口
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    public List<ProductInfo> findByProductStatus(Integer status);

    public ProductInfo findByProductId(String productId);
}
