package com.tmall.service;

import com.tmall.packPojo.PropertyPack;
import com.tmall.pojo.Property;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
public interface PropertyService {
    void addProperty(Property p);

    void updateProperty(Property p);

    void deleteProperty(Integer id);

    PropertyPack findPropertyListById(Integer id);

    List<PropertyPack> findPropertyList(Integer cid, Integer startPage, Integer rows);

    Integer findPropertyTotalNumber(Integer cid);

    List<Property> findPropertyListByCid(Integer cid);
}
