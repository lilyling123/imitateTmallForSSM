package com.tmall.packPojo;

import com.tmall.pojo.OrderItem;
import com.tmall.pojo.User;

/**
 * Created by lily_ling on 2017/6/24.
 */
public class OrderItemPack extends OrderItem {
    private ProductPack product;

    private OrderPack order;

    private User user;

    private OrderItem orderItem;

    public OrderItemPack() {

    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        if (orderItem != null) {
            this.setId(orderItem.getId());
            this.setUid(orderItem.getUid());
            this.setNumber(orderItem.getNumber());
            this.setOid(orderItem.getOid());
            this.setPid(orderItem.getPid());
        }
        this.orderItem = orderItem;
    }


    public ProductPack getProduct() {
        return product;
    }

    public void setProduct(ProductPack product) {
        this.product = product;
    }

    public OrderPack getOrder() {
        return order;
    }

    public void setOrder(OrderPack order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
