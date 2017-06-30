package com.tmall.service;

import com.tmall.packPojo.CategoryPack;
import com.tmall.pojo.Category;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
public interface CategoryService {

    List<CategoryPack> findCategoryPackList();

    List<Category> findCategoryList(Integer start, Integer count);

    Integer findCategoryTotalNumber();

    CategoryPack selectCategoryById(Integer cid);

    void deleteCategoryById(Integer id);

    void addCategory(Category category);

    void updateCategory(Category category);
}
