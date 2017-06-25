package com.tmall.packPojo;

import com.tmall.pojo.Category;
import com.tmall.pojo.Property;

import java.io.Serializable;

/**
 * Created by lily_ling on 2017/6/24.
 */
public class PropertyPack extends Property implements Serializable {
    private Category category;
    private Property property;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        if (property != null) {
            this.setId(property.getId());
            this.setCid(property.getCid());
            this.setName(property.getName());
        }
        this.property = property;
    }

    public PropertyPack() {

    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}
