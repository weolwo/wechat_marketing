package com.wechat.marketing.service;

import com.wechat.marketing.pojo.ProductCategory;

import java.util.List;

/**
 * 分类接口类
 */
public interface CategoryService {

    /**
     * 查询某条数据
     * @return
     */
    public ProductCategory findById(Integer categoryId);

    /**
     * 查询所有分类列表
     * @return
     */
    public List<ProductCategory> findAll();

    /**
     * 通过类目编号查询分类列表信息
     * @param categoryTypeList
     * @return
     */
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 保存或者更新分类
     * @param productCategory
     * @return
     */
    public ProductCategory save(ProductCategory productCategory);
}
