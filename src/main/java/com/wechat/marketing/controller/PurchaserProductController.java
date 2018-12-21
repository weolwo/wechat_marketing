package com.wechat.marketing.controller;

import com.wechat.marketing.pojo.ProductCategory;
import com.wechat.marketing.pojo.ProductInfo;
import com.wechat.marketing.service.CategoryService;
import com.wechat.marketing.service.ProductInfoService;
import com.wechat.marketing.utils.Result;
import com.wechat.marketing.view.ProductInfoVo;
import com.wechat.marketing.view.ProductVo;
import com.wechat.marketing.view.ResultVo;
import com.wechat.marketing.view.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 买家商品控制层
 */
@RestController
@RequestMapping("/buyer/product")
public class PurchaserProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    public ResultVo findPage() {

        //1.查询所有上架的商品
        List<ProductInfo> productInfoList = productInfoService.findAllUp();

        List<Integer> productCategoryTypeList = new ArrayList<>();

        List<ProductVo> productVoList = new ArrayList<>();
        //获取已上架所有商品类目编号
        for (ProductInfo productInfo : productInfoList) {
            productCategoryTypeList.add(productInfo.getCategoryType());
        }
        //2.查询所有的类目信息
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(productCategoryTypeList);
        //3.数据封装
        for (ProductCategory productCategory : categoryList) {
            ProductVo productVo = new ProductVo();
            productVo.setCategorType(productCategory.getCategoryType());
            productVo.setCategoryName(productCategory.getCategoryName());

            //封装商品详情信息
            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().intValue() == productCategory.getCategoryType().intValue()) {

                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo, productInfoVo);//深克隆
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setProductInfoList(productInfoVoList);
            productVoList.add(productVo);
        }

        return Result.success(productVoList);
    }
}
