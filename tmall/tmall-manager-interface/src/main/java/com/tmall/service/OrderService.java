package com.tmall.service;

import com.tmall.packPojo.OrderPack;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/28.
 */
public interface OrderService {
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    public List<OrderPack> findOrderPacks(Integer startPage, Integer rows);

    Integer findOrdersNumber();

    void updateOrder(Integer id);
}
