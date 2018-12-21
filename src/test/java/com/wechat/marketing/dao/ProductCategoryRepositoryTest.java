package com.wechat.marketing.dao;

import com.wechat.marketing.pojo.ProductCategory;
import org.hibernate.criterion.Example;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;


    @Test
    public void findByCategoryTypeIn() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<ProductCategory> categoryList = repository.findByCategoryTypeIn(list);
        org.junit.Assert.assertNotEquals(0, list.size());
        System.out.println(categoryList);
    }

    public void findOne() {

        ProductCategory category = repository.findById(1).get();

    }
}