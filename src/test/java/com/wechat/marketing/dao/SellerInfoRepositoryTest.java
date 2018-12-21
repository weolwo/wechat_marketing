package com.wechat.marketing.dao;

import com.wechat.marketing.pojo.SellerInfo;
import com.wechat.marketing.utils.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void save() {
        SellerInfo seller=new SellerInfo();
        seller.setId(UUIDUtil.generateUUID());
        seller.setUsername("小腿猫");
        seller.setPassword("123456");
        seller.setOpenid(UUIDUtil.generateUUID());
        repository.save(seller);
    }

    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo = repository.findByOpenid("1538622677798");
        System.out.println(sellerInfo);
    }
}