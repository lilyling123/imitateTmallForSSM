package com.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.tmall.dao.CategoryMapper;
import com.tmall.dao.PropertyMapper;
import com.tmall.packPojo.PropertyPack;
import com.tmall.pojo.Category;
import com.tmall.pojo.Property;
import com.tmall.pojo.PropertyExample;
import com.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyMapper propertyMapper;
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<PropertyPack> findPropertyList(Integer cid, Integer startPage, Integer rows) {
        //设置分页
        PageHelper.startPage(startPage, rows);
        //设置查询条件
        PropertyExample example = new PropertyExample();

        example.setOrderByClause("id");

        example.createCriteria().andCidEqualTo(cid);
        //执行查询
        List<Property> list = propertyMapper.selectByExample(example);

        //绑定Category
        Category c = categoryMapper.selectByPrimaryKey(cid);

        List<PropertyPack> propertyPacks = new ArrayList<>();
        for (Property property : list) {
            if (property == null) {
                continue;
            }
            //包装成PropertyPack对象
            PropertyPack propertyPack = new PropertyPack();

            propertyPack.setProperty(property);
            //将Category和Property绑定
            propertyPack.setCategory(c);
            propertyPacks.add(propertyPack);

        }
        return propertyPacks;
    }


    @Override
    public Integer findPropertyTotalNumber(Integer cid) {
        //设置查询条件
        PropertyExample example = new PropertyExample();

        example.createCriteria().andCidEqualTo(cid);
        //执行查询
        return propertyMapper.countByExample(example);
    }

    @Override
    public List<Property> findPropertyListByCid(Integer cid) {
        //设置查询条件
        PropertyExample example = new PropertyExample();

        example.createCriteria().andCidEqualTo(cid);
        //执行查询
        return propertyMapper.selectByExample(example);
    }
}
