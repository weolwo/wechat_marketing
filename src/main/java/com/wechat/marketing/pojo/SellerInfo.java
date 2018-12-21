package com.wechat.marketing.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 商家实体数据模型
 */
@Data
@Entity
@DynamicUpdate
public class SellerInfo {

    @Id
    private String id;

    private String username;

    private String password;

    private String openid;
}
