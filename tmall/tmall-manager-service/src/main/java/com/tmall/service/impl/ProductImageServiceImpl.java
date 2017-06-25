package com.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.tmall.dao.ProductImageMapper;
import com.tmall.dao.ProductMapper;
import com.tmall.packPojo.ProductImagePack;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductImage;
import com.tmall.pojo.ProductImageExample;
import com.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageMapper productImageMapper;

    @Override
    public void updateProductImage(ProductImage pi) {
        productImageMapper.updateByPrimaryKey(pi);
    }

    @Override
    public void addProductImage(ProductImage pi) {
        productImageMapper.insertSelective(pi);
    }

    @Override
    public void deleteProductImage(Integer id) {
        productImageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ProductImagePack selectProductImagePackById(Integer id) {
        ProductImage pi = productImageMapper.selectByPrimaryKey(id);
        ProductImagePack pack = new ProductImagePack();
        pack.setProductImage(pi);
        pack.setProduct(productMapper.selectByPrimaryKey(pi.getPid()));

        return pack;
    }

    @Override
    public List<ProductImagePack> findProductImageByPid(Integer pid, String type, int startPage, int rows) {
        //设置分页
        PageHelper.startPage(startPage, rows);

        //设置查询条件
        ProductImageExample example = new ProductImageExample();
        example.setOrderByClause("id desc");
        ProductImageExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        criteria.andTypeEqualTo(type);

        Product p = productMapper.selectByPrimaryKey(pid);
        //执行查询和设置结果集
        List<ProductImage> productImages = productImageMapper.selectByExample(example);

        List<ProductImagePack> result = new ArrayList<>();

        for (ProductImage productImage : productImages) {
            if (productImage == null) {
                continue;
            }
            //包装成ProductImagePack对象
            ProductImagePack productImagePack = new ProductImagePack();

            productImagePack.setProductImage(productImage);

            productImagePack.setProduct(p);

            result.add(productImagePack);

        }

        return result;
    }


}

