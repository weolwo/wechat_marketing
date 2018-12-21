package com.wechat.marketing.dao;

import com.wechat.marketing.pojo.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {

    public SellerInfo findByOpenid(String openid);
}
