package com.wechat.marketing.service.impl;

import com.wechat.marketing.pojo.ProductInfo;
import com.wechat.marketing.service.ProductInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class productInfoServiceImplTest {

    @Autowired
    private ProductInfoService productInfoService;
    @Test
    public void findAllUp() {
        List<ProductInfo> productInfoList = productInfoService.findAllUp();
        System.out.println(productInfoList.size());
    }

    @Test
    public void findById() {
        ProductInfo productInfo = productInfoService.findById("22333333");
        System.out.println(productInfo);
    }

    @Test
    public void findAll() {
        Pageable pageable=PageRequest.of(0,5);
        Page<ProductInfo> page = productInfoService.findAll(pageable);
        System.out.println(page.getContent().size());
    }

    @Test
    public void save() {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("665666232");
        productInfo.setCategoryType(5);
        productInfo.setProductDescription("小腿猫");
        productInfo.setProductIcon("htttp:\\xxxxxx.jpg");
        productInfo.setProductName("豆瓣酱");
        productInfo.setProductPrice(new BigDecimal(288.2));
        productInfo.setProductStatus(0);
        productInfo.setProductStoct(999);
        productInfoService.save(productInfo);
    }

    @Test
    public void offSale(){
        ProductInfo productInfo = productInfoService.findById("22333333");
        productInfoService.offSale(productInfo.getProductId());
    }

    @Test
    public void onSale(){
        ProductInfo productInfo = productInfoService.findById("22333333");
        productInfoService.onSale(productInfo.getProductId());
    }
}