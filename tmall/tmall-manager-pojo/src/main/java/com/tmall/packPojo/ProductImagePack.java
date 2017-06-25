package com.tmall.packPojo;

import com.tmall.pojo.Product;
import com.tmall.pojo.ProductImage;

/**
 * Created by lily_ling on 2017/6/24.
 */
public class ProductImagePack extends ProductImage {
    private Product product;
    private ProductImage productImage;

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        if (productImage != null) {
            this.setId(productImage.getId());
            this.setPid(productImage.getPid());
            this.setType(productImage.getType());
            this.setImgUrl(productImage.getImgUrl());
        }
        this.productImage = productImage;
    }

    public ProductImagePack() {

    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
