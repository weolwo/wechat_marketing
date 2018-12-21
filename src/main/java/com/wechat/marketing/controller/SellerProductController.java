package com.wechat.marketing.controller;

import com.wechat.marketing.enums.ResultEnum;
import com.wechat.marketing.exception.SellException;
import com.wechat.marketing.pojo.ProductCategory;
import com.wechat.marketing.pojo.ProductInfo;
import com.wechat.marketing.service.CategoryService;
import com.wechat.marketing.service.ProductInfoService;
import com.wechat.marketing.utils.UUIDUtil;
import com.wechat.marketing.validation.ProductForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 商家商品控制层
 */
@RestController
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询商品列表
     *
     * @param page
     * @param size
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(pageable);
        map.put("productList", productInfoPage.getContent());
        map.put("totalPage", productInfoPage.getTotalPages());
        map.put("size", size);
        map.put("curPage", page);
        return new ModelAndView("product/list", map);
    }

    /**
     * 商家商品上架
     *
     * @return
     */
    @RequestMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId, Map<String, Object> map) {
        ProductInfo productInfo = null;
        try {
            productInfo = productInfoService.findById(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        productInfoService.onSale(productInfo.getProductId());
        map.put("msg", ResultEnum.SUCCESS.getMessage());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 商家商品下架
     *
     * @return
     */
    @RequestMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId, Map<String, Object> map) {

        try {
            ProductInfo productInfo = productInfoService.findById(productId);
            productInfoService.offSale(productInfo.getProductId());
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.SUCCESS.getMessage());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId, Map<String, Object> map) {
        if (!StringUtils.isEmpty(productId)) {//减少对数据库的压力
            ProductInfo productInfo = productInfoService.findById(productId);
            map.put("product", productInfo);
        }

        //查询所有类目信息
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("product/index", map);
    }

    /**
     * 保存商品信息
     *
     * @return
     */
    @RequestMapping("/save")
    public ModelAndView save(@Valid ProductForm form, BindingResult bindingResult, Map<String, Object> map) {

        if (bindingResult.hasErrors()) {//校验失败情况下的反馈信息
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            if (!StringUtils.isEmpty(form.getProductId())) {

                //查询商品信息
                productInfo = productInfoService.findById(form.getProductId());
            }
            form.setProductId(UUIDUtil.generateUUID());//设置商品id
            BeanUtils.copyProperties(form, productInfo);
            productInfoService.save(productInfo);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }
}
