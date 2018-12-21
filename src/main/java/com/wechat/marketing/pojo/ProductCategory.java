package com.wechat.marketing.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品分类实体数据模型
 */
@Entity
@DynamicUpdate//动态更新
@Data//包括生产get set toString等方法
public class ProductCategory implements Serializable {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY )//IDENTITY ：主键是由数据库生成的，例如数据库中设置了主键自增。
    private Integer categoryId;//分类id

    private String categoryName; //分类名称

    private Integer categoryType; //分类编号

    private Date createTime;

    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
