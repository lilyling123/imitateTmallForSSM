package com.tmall.service;

import com.tmall.packPojo.OrderPack;
import com.tmall.pojo.Order;

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

    Order addOrder(Order order);

    OrderPack findOrderPackById(Integer id);

    List<OrderPack> findOrderPackByUId(Integer uid);

    void deleteOrderByOrderId(Order order);

    public List<OrderPack> findOrderPacks(Integer startPage, Integer rows);

    Integer findOrdersNumber();

    void updateOrder(Order order);
}
