package com.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.tmall.dao.CategoryMapper;
import com.tmall.pojo.Category;
import com.tmall.pojo.CategoryExample;
import com.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findCategoryList(Integer startPage, Integer rows) {
        //设置分页信息
        PageHelper.startPage(startPage, rows);
        //执行查询
        CategoryExample categoryExample = new CategoryExample();
        List<Category> categories = categoryMapper.selectByExample(categoryExample);

        return categories;
    }

    @Override
    public Integer findCategoryTotalNumber() {
        //执行查询
        CategoryExample categoryExample = new CategoryExample();
        return categoryMapper.countByExample(categoryExample);
    }

    @Override
    public Category selectCategoryById(Integer cid) {

        return categoryMapper.selectByPrimaryKey(cid);
    }

    @Override
    public void deleteCategoryById(Integer id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void addCategory(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

}
