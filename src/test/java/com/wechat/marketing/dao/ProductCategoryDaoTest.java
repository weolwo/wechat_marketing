package com.wechat.marketing.dao;

import com.wechat.marketing.pojo.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest() {
        Optional<ProductCategory> category = repository.findById(1);
        System.out.println(category);
    }

    @Test
    public void saveTest() {
        Optional<ProductCategory> category = repository.findById(3);
        ProductCategory category1 = category.get();
        category1.setCategoryType(5);
        repository.save(category1);
        System.out.println(category1);
    }
}