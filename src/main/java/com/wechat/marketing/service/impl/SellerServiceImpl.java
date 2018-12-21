package com.wechat.marketing.service.impl;

import com.wechat.marketing.dao.SellerInfoRepository;
import com.wechat.marketing.enums.ResultEnum;
import com.wechat.marketing.exception.SellException;
import com.wechat.marketing.pojo.SellerInfo;
import com.wechat.marketing.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findByOpenid(String openid) {
        SellerInfo sellerInfo = sellerInfoRepository.findByOpenid(openid);
        if (sellerInfo==null){
            throw new SellException(ResultEnum.LOGIN_FAIL);
        }
        return sellerInfo;
    }
}
