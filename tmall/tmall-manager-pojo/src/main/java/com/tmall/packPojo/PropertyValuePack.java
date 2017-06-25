package com.tmall.packPojo;

import com.tmall.pojo.Product;
import com.tmall.pojo.Property;
import com.tmall.pojo.PropertyValue;

/**
 * Created by lily_ling on 2017/6/24.
 */
public class PropertyValuePack extends PropertyValue {
    private Product product;
    private Property property;
    private PropertyValue propertyValue;

    public PropertyValue getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(PropertyValue propertyValue) {
        if (propertyValue != null) {
            this.setId(propertyValue.getId());
            this.setPid(propertyValue.getPid());
            this.setPtid(propertyValue.getPtid());
            this.setValue(propertyValue.getValue());
        }
        this.propertyValue = propertyValue;
    }

    public PropertyValuePack() {

    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
