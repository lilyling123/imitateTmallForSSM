package com.tmall.packPojo;

import com.tmall.pojo.Category;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductImage;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
public class ProductPack extends Product {
    private Category category;

    private ProductImage firstProductImage;

    private List<ProductImage> productImages;

    private List<ProductImage> productSingleImages;

    private List<ProductImage> productDetailImages;

    private int reviewCount;

    private int saleCount;

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if (product != null) {
            this.setId(product.getId());
            this.setName(product.getName());
            this.setCid(product.getCid());
            this.setCreateDate(product.getCreateDate());
            this.setOrignalPrice(product.getOrignalPrice());
            this.setPromotePrice(product.getPromotePrice());
            this.setStock(product.getStock());
            this.setSubTitle(product.getSubTitle());
        }
        this.product = product;
    }

    public ProductPack() {

    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductImage getFirstProductImage() {
        return firstProductImage;
    }

    public void setFirstProductImage(ProductImage firstProductImage) {
        this.firstProductImage = firstProductImage;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public List<ProductImage> getProductSingleImages() {
        return productSingleImages;
    }

    public void setProductSingleImages(List<ProductImage> productSingleImages) {
        this.productSingleImages = productSingleImages;
    }

    public List<ProductImage> getProductDetailImages() {
        return productDetailImages;
    }

    public void setProductDetailImages(List<ProductImage> productDetailImages) {
        this.productDetailImages = productDetailImages;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }
}
