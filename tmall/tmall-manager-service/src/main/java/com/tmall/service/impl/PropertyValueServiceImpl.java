package com.tmall.service.impl;

import com.tmall.dao.ProductMapper;
import com.tmall.dao.PropertyMapper;
import com.tmall.dao.PropertyValueMapper;
import com.tmall.packPojo.PropertyValuePack;
import com.tmall.pojo.Product;
import com.tmall.pojo.Property;
import com.tmall.pojo.PropertyValue;
import com.tmall.pojo.PropertyValueExample;
import com.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lily_ling on 2017/6/25.
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    private PropertyValueMapper propertyValueMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValueMapper.insertSelective(propertyValue);
    }

    @Override
    public PropertyValuePack selectPropertyValueById(Integer pvId) {
        PropertyValue pv = propertyValueMapper.selectByPrimaryKey(pvId);
        PropertyValuePack pack = new PropertyValuePack();
        pack.setPropertyValue(pv);
        pack.setProduct(productMapper.selectByPrimaryKey(pv.getPid()));
        pack.setProperty(propertyMapper.selectByPrimaryKey(pv.getPtid()));

        return pack;
    }

    @Override
    public void updatePropertyValue(PropertyValue pv) {
        propertyValueMapper.updateByPrimaryKeySelective(pv);
    }

    @Override
    public List<PropertyValuePack> findPropertyValueList(Integer pid) {
        //设置查询条件
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        //开始查询
        List<PropertyValue> propertyValueLists = propertyValueMapper.selectByExample(example);

        List<PropertyValuePack> results = new ArrayList<>();
        for (PropertyValue propertyValue : propertyValueLists) {

            PropertyValuePack pack = new PropertyValuePack();
            //查找属性
            Property property = propertyMapper.selectByPrimaryKey(propertyValue.getPtid());
            Product product = productMapper.selectByPrimaryKey(propertyValue.getPid());
            //设置属性
            pack.setPropertyValue(propertyValue);
            pack.setProperty(property);
            pack.setProduct(product);

            //添加到结果集中
            results.add(pack);
        }
        return results;
    }

    @Override
    public PropertyValuePack selectPropertyValuePackByIdAndPid(Integer pid, Integer ptid) {
        //设置查询条件
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid).andPtidEqualTo(ptid);
        //开始查询
        List<PropertyValue> list = propertyValueMapper.selectByExample(example);

        if (list != null && list.size() > 0) {
            PropertyValue pv = list.get(0);
            if (null == pv)
                return null;
            PropertyValuePack pack = new PropertyValuePack();
            pack.setPropertyValue(pv);

            Product p = productMapper.selectByPrimaryKey(pv.getPid());
            Property property = propertyMapper.selectByPrimaryKey(pv.getPtid());

            pack.setProduct(p);
            pack.setProperty(property);

            return pack;
        }
        return null;
    }
}
