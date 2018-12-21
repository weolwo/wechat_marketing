package com.wechat.marketing.service;

import com.wechat.marketing.dto.CartDTO;
import com.wechat.marketing.pojo.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品服务层接口
 */
public interface ProductInfoService {

    /**
     * 查询已上架的商品列表
     *
     * @return
     */
    public List<ProductInfo>  findAllUp();

    /**
     * 查询某个商品
     * @param productId
     * @return
     */
    public ProductInfo findById(String productId);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    public Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 保存或者更新商品信息
     * @param productInfo
     * @return
     */
    public ProductInfo save(ProductInfo productInfo);

    /**
     * 增加库存
     * @param cartDTOList
     */
    public void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减少库存
     * @param cartDTOList
     */
    public void decreaseStock(List<CartDTO> cartDTOList);

    /**
     * 商品下架
     * @param productId
     * @return
     */
    public ProductInfo offSale(String productId);

    /**
     * 商品上架
     * @param productId
     * @return
     */
    public ProductInfo onSale(String productId);
}
