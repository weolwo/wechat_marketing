package com.wechat.marketing.dao;

import com.wechat.marketing.pojo.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品分类接口
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    public ProductCategory findByCategoryId(Integer categoryId);
}
