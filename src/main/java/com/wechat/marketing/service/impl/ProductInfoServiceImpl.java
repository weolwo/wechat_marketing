package com.wechat.marketing.service.impl;

import com.wechat.marketing.dao.ProductInfoRepository;
import com.wechat.marketing.dto.CartDTO;
import com.wechat.marketing.enums.ProductStatusEnum;
import com.wechat.marketing.enums.ResultEnum;
import com.wechat.marketing.exception.SellException;
import com.wechat.marketing.pojo.ProductInfo;
import com.wechat.marketing.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    /**
     * 查询已上架的商品列表
     *
     * @return
     */
    @Override
    public List<ProductInfo> findAllUp() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    /**
     * 查询某个商品
     *
     * @param productId
     * @return
     */
    @Override
    public ProductInfo findById(String productId) {
        return repository.findByProductId(productId);
    }

    /**
     * 分页查询
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * 保存或者更新商品信息
     *
     * @param productInfo
     * @return
     */
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            //查询商品信息
            ProductInfo productInfo = repository.findByProductId(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            productInfo.setProductStoct(productInfo.getProductStoct() + cartDTO.getProductQuantity());
            repository.save(productInfo);
        }

    }

    @Override
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            //查询商品信息
            ProductInfo productInfo = repository.findByProductId(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            if (productInfo.getProductStoct() - cartDTO.getProductQuantity() < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStoct(productInfo.getProductStoct() - cartDTO.getProductQuantity());
            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findByProductId(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if (productInfo.getProductStatusEnum().getCode() == ProductStatusEnum.DOWN.getCode()) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());

        return repository.save(productInfo);
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findByProductId(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if (productInfo.getProductStatusEnum().getCode() == ProductStatusEnum.UP.getCode()) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());

        return repository.save(productInfo);
    }
}
