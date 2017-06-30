package com.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.tmall.dao.CategoryMapper;
import com.tmall.packPojo.CategoryPack;
import com.tmall.packPojo.ProductPack;
import com.tmall.pojo.Category;
import com.tmall.pojo.CategoryExample;
import com.tmall.service.CategoryService;
import com.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductService productService;

    @Override
    public List<CategoryPack> findCategoryPackList() {

        int productNumberEachRow = 8;

        CategoryExample example = new CategoryExample();
        CategoryExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<Category> list = categoryMapper.selectByExample(example);
        List<CategoryPack> res = new ArrayList<>();

        for (Category c : list) {

            CategoryPack pack = new CategoryPack();

            pack.setCategory(c);

            //设置和填充属性
            List<ProductPack> productPacks = productService.findProductListByCid(c.getId(), 1, Short.MAX_VALUE);
            pack.setProducts(productPacks);

            //设置和填充属性
            List<List<ProductPack>> productsByRow = new ArrayList<>();
            for (int i = 0; i < productPacks.size(); i += productNumberEachRow) {

                int size = i + productNumberEachRow;

                size = size > productPacks.size() ? productPacks.size() : size;

                List<ProductPack> productsOfEachRow = productPacks.subList(i, size);

                productsByRow.add(productsOfEachRow);

            }
            pack.setProductsByRow(productsByRow);

            res.add(pack);
        }

        return res;
    }

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
    public CategoryPack selectCategoryById(Integer cid) {
        int productNumberEachRow = 8;
        Category c = categoryMapper.selectByPrimaryKey(cid);
        CategoryPack pack = new CategoryPack();
        pack.setCategory(c);

        return pack;
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
