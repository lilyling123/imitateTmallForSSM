package com.tmall.service;

import com.tmall.packPojo.ProductPack;
import com.tmall.pojo.Product;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
public interface ProductService {

    Integer findProductListTotalNumber(Integer cid);

    List<ProductPack> findProductListByCid(Integer cid, int startPage, int rows);

    void addProduct(Product p);

    void deleteProductById(Integer pid);

    ProductPack selectProductById(Integer pid);

    void updateProduct(Product p);
}
