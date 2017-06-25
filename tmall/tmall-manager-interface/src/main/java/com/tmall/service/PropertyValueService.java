package com.tmall.service;

import com.tmall.packPojo.PropertyValuePack;
import com.tmall.pojo.PropertyValue;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/25.
 */
public interface PropertyValueService {
    void addPropertyValue(PropertyValue propertyValue);

    PropertyValuePack selectPropertyValueById(Integer pvId);

    void updatePropertyValue(PropertyValue pv);

    List<PropertyValuePack> findPropertyValueList(Integer pid);

    PropertyValuePack selectPropertyValuePackByIdAndPid(Integer pid, Integer ptid);
}
