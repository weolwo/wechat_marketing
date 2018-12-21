package com.wechat.marketing.dao;

import com.wechat.marketing.pojo.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void findByProductStatus() {
        List<ProductInfo> list = repository.findByProductStatus(0);
        System.out.println(list.size());
    }
    @Test
    public void save(){
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("22333333");
        productInfo.setCategoryType(2);
        productInfo.setProductDescription("美猫");
        productInfo.setProductIcon("htttp:\\hjfhffh.jpg");
        productInfo.setProductName("鱼香肉丝");
        productInfo.setProductPrice(new BigDecimal(23.2));
        productInfo.setProductStatus(0);
        productInfo.setProductStoct(99);
       repository.save(productInfo);
    }
}