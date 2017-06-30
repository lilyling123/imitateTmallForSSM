package com.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.tmall.dao.ProductMapper;
import com.tmall.packPojo.CategoryPack;
import com.tmall.packPojo.ProductImagePack;
import com.tmall.packPojo.ProductPack;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductExample;
import com.tmall.service.CategoryService;
import com.tmall.service.ProductImageService;
import com.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductImageService productImageServiceImpl;

    @Override
    public void addProduct(Product p) {
        p.setCreateDate(new Date());
        productMapper.insert(p);
    }

    @Override
    public void deleteProductById(Integer pid) {
        productMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public ProductPack selectProductById(Integer pid) {
        Product p = productMapper.selectByPrimaryKey(pid);
        ProductPack pack = new ProductPack();
        pack.setProduct(p);

        setFirstProductImage(pack);
        return pack;
    }

    @Override
    public void updateProduct(Product p) {
        productMapper.updateByPrimaryKey(p);
    }

    @Override
    public Integer findProductListTotalNumber(Integer cid) {

        ProductExample example = new ProductExample();

        example.createCriteria().andCidEqualTo(cid);

        return productMapper.countByExample(example);
    }

    @Override
    public List<ProductPack> findProductListByCid(Integer cid, int startPage, int rows) {
        //设置分页
        PageHelper.startPage(startPage, rows);
        //设置查询条件
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        //执行查询
        List<Product> products = productMapper.selectByExample(example);

        //设置结果集
        List<ProductPack> result = new ArrayList<>();
        for (Product p : products) {
            if (p == null) {
                continue;
            }
            //包装成proPack对象
            ProductPack proPack = new ProductPack();
            proPack.setProduct(p);
            CategoryPack c = categoryService.selectCategoryById(p.getCid());
            proPack.setCategory(c);
            setFirstProductImage(proPack);

            result.add(proPack);
        }

        return result;
    }


    //将图片放到Product中
    private void setFirstProductImage(ProductPack p) {
//        ProductImageServiceImpl productImageServiceImpl = new ProductImageServiceImpl();

        List<ProductImagePack> pis = productImageServiceImpl.findProductImageByPid(p.getId(), ProductImageService.type_single, 0, Short.MAX_VALUE);
        if (!pis.isEmpty())
            p.setFirstProductImage(pis.get(0));
    }
}
