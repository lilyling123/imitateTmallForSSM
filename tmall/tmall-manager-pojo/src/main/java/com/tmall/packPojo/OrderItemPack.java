package com.tmall.packPojo;

import com.tmall.pojo.Order;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.Product;
import com.tmall.pojo.User;

/**
 * Created by lily_ling on 2017/6/24.
 */
public class OrderItemPack extends OrderItem {
    private Product product;

    private Order order;

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


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
