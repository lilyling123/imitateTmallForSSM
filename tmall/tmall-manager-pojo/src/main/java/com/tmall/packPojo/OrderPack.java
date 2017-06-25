package com.tmall.packPojo;

import com.tmall.pojo.Order;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.User;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
public class OrderPack extends Order {

    private List<OrderItem> orderItems;

    private User user;

    private float total;

    private int totalNumber;

    private String statusDesc;
    private Order order;

    public OrderPack() {

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        if (order != null) {
            this.setId(order.getId());
            this.setStatus(order.getStatus());
            this.setAddress(order.getAddress());
            this.setConfirmDate(order.getConfirmDate());
            this.setCreateDate(order.getCreateDate());
            this.setDeliveryDate(order.getDeliveryDate());
            this.setMobile(order.getMobile());
            this.setOrderCode(order.getOrderCode());
            this.setPayDate(order.getPayDate());
            this.setPost(order.getPost());
            this.setReceiver(order.getReceiver());
            this.setUid(order.getUid());
            this.setUserMessage(order.getUserMessage());
        }
        this.order = order;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
