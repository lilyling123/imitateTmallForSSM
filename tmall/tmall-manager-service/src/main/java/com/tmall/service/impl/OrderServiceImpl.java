package com.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.tmall.dao.OrderItemMapper;
import com.tmall.dao.OrderMapper;
import com.tmall.dao.UserMapper;
import com.tmall.packPojo.OrderItemPack;
import com.tmall.packPojo.OrderPack;
import com.tmall.pojo.Order;
import com.tmall.pojo.OrderExample;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.OrderItemExample;
import com.tmall.service.OrderService;
import com.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lily_ling on 2017/6/28.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductService productService;

    @Override
    public OrderPack findOrderPackById(Integer id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        if (order == null) return null;
        OrderPack pack = new OrderPack();


        return pack;
    }


    @Override
    public List<OrderPack> findOrderPacks(Integer startPage, Integer rows) {
        //查找到对应结果集
        PageHelper.startPage(startPage, rows);
        List<Order> orders = orderMapper.selectByExample(new OrderExample());
        //设置真正的结果集
        List<OrderPack> res = new ArrayList<>();
        for (Order order : orders) {
            OrderPack pack = new OrderPack();

            fillOrderPack(pack, order);

            res.add(pack);

        }
        return res;
    }
    //将OrderPack中的各项属性填充
    private void fillOrderPack(OrderPack pack, Order order) {
        pack.setOrder(order);
        //查找到对应的orderItems
        List<OrderItem> orderItems = orderItemMapper.selectByExample(new OrderItemExample());

        List<OrderItemPack> packs = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemPack itemPack = new OrderItemPack();
            //设置itempack中的属性
            itemPack.setOrderItem(orderItem);

            itemPack.setOrder(pack);
            itemPack.setProduct(productService.selectProductById(orderItem.getPid()));
            itemPack.setUser(userMapper.selectByPrimaryKey(orderItem.getUid()));

            packs.add(itemPack);
        }
        //计算数值
        float total = 0;
        int totalNumber = 0;

        for (OrderItemPack oi : packs) {
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
            totalNumber += oi.getNumber();
        }
        //设置属性
        pack.setTotal(total);
        pack.setTotalNumber(totalNumber);
        pack.setUser(userMapper.selectByPrimaryKey(order.getUid()));

        pack.setOrderItems(packs);
    }

    @Override
    public Integer findOrdersNumber() {
        OrderExample example = new OrderExample();
        return orderMapper.countByExample(example);
    }

    @Override
    public void updateOrder(Integer id) {

        Order o = orderMapper.selectByPrimaryKey(id);
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.waitConfirm);
        orderMapper.updateByPrimaryKey(o);
    }
}
