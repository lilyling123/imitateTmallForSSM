package com.tmall.service;

import com.tmall.packPojo.ProductImagePack;
import com.tmall.pojo.ProductImage;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
public interface ProductImageService {
    String type_single = "type_single";
    String type_detail = "type_detail";

    void addProductImage(ProductImage pi);

    void deleteProductImage(Integer id);

    ProductImagePack selectProductImagePackById(Integer id);

    List<ProductImagePack> findProductImageByPid(Integer pid, String type, int startPage, int rows);

    void updateProductImage(ProductImage pi);

}
