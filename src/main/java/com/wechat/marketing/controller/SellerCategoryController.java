package com.wechat.marketing.controller;

import com.wechat.marketing.enums.ResultEnum;
import com.wechat.marketing.exception.SellException;
import com.wechat.marketing.pojo.ProductCategory;
import com.wechat.marketing.service.CategoryService;
import com.wechat.marketing.validation.CategoryForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 商家分类控制层
 */
@RequestMapping("/seller/category")
@RestController
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询分类列表
     *
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    /**
     * 获取新增集修改页面
     *
     * @param categoryId
     * @param map
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId, Map<String, Object> map) {
        if (categoryId != null) {
            //查询分类信息
            ProductCategory productCategory = categoryService.findById(categoryId);
            map.put("category", productCategory);
        }
        return new ModelAndView("category/index", map);
    }

    /**
     * 保存分类
     * @param categoryForm
     * @param bindingResult
     * @param map
     * @return
     */
    @RequestMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult bindingResult, Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/category/list");
            new ModelAndView("common/error", map);
        }
        ProductCategory productCategory = new ProductCategory();
        if (categoryForm.getCategoryId() != null) {
            productCategory = categoryService.findById(categoryForm.getCategoryId());
        }
        try {
            BeanUtils.copyProperties(categoryForm, productCategory);
            categoryService.save(productCategory);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/list");
            new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.SUCCESS.getMessage());
        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("common/success", map);
    }
}
