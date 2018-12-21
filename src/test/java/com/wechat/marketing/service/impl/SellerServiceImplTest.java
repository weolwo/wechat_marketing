package com.wechat.marketing.service.impl;

import com.wechat.marketing.pojo.SellerInfo;
import com.wechat.marketing.service.SellerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

    @Autowired
    private SellerService sellerService;
    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo = sellerService.findByOpenid("1538622677798");
        System.out.println(sellerInfo);
    }
}