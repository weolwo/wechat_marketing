package com.wechat.marketing.service.impl;

import com.wechat.marketing.pojo.ProductCategory;
import com.wechat.marketing.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findById() {

        ProductCategory category = categoryService.findById(1);
        System.out.println(category);

    }

    @Test
    public void findAll() {
        List<ProductCategory> categoryList = categoryService.findAll();
        System.out.println(categoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 5));
        System.out.println(categoryList.size());
    }

    @Test
    public void save() {

        ProductCategory category = categoryService.findById(1);
        category.setCategoryName("小吃");
        categoryService.save(category);
    }
}