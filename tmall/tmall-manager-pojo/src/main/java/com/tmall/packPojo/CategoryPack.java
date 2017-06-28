package com.tmall.packPojo;

import com.tmall.pojo.Category;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
public class CategoryPack extends Category {
    private List<ProductPack> products;
    private List<List<ProductPack>> productsByRow;
    private Category category;

    public CategoryPack() {

    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category c) {
        this.category = c;
        if (category != null) {
            this.setName(category.getName());
            this.setId(category.getId());
            this.setImgUrl(category.getImgUrl());
        }

    }

    public List<ProductPack> getProducts() {
        return products;
    }

    public void setProducts(List<ProductPack> products) {
        this.products = products;
    }

    public List<List<ProductPack>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<ProductPack>> productsByRow) {
        this.productsByRow = productsByRow;
    }
}
