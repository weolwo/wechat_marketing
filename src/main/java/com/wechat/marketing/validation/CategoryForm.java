package com.wechat.marketing.validation;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryForm {
    private Integer categoryId;//分类id

    private String categoryName; //分类名称

    private Integer categoryType; //分类编号

}
