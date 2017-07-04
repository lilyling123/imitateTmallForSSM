package com.tmall.service;

import com.tmall.packPojo.OrderItemPack;
import com.tmall.pojo.OrderItem;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/29.
 */
public interface OrderItemService {

    int findTotalOrderItemNumber();

    OrderItem addOrderItem(OrderItem orderItem);

    void updateOrderItem(OrderItem orderItem);

    void deleteOrderItem(Integer id);

    OrderItemPack findOrderItemPackById(Integer id);

    List<OrderItemPack> findOrderItemPackByUid(Integer uid);

    List<OrderItemPack> findOrderItemPackByPid(Integer pid);

    List<OrderItemPack> findOrderItemPackByOid(Integer oid);

    int getProductSaleCount(int pid);


    void deleteOrdetItemByOrderItemId(Integer oiid);
}
