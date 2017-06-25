package com.tmall.packPojo;

import com.tmall.pojo.Category;
import com.tmall.pojo.Product;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
public class CategoryPack extends Category {
    private List<Product> products;
    private List<List<Product>> productsByRow;
    private Category category;

    public CategoryPack() {

    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category c) {
        if (category != null) {
            this.setName(category.getName());
            this.setId(category.getId());
            this.setImgUrl(c.getImgUrl());
        }
        this.category = c;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }
}
