package com.tmall.service;

import com.tmall.packPojo.OrderItemPack;
import com.tmall.pojo.OrderItem;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/29.
 */
public interface OrderItemService {

    int findTotalOrderItemNumber();

    void addOrderItem(OrderItem orderItem);

    void updateOrderItem(OrderItem orderItem);

    void deleteOrderItem(Integer id);

    OrderItemPack findOrderItemPackById(Integer id);

    List<OrderItemPack> findOrderItemPackByUid(Integer uid, Integer startPage, Integer count);

    List<OrderItemPack> findOrderItemPackByPid(Integer pid, Integer startPage, Integer rows);

    List<OrderItemPack> findOrderItemPackByOid(Integer oid, Integer startPage, Integer rows);

    int getProductSaleCount(int pid);
}
