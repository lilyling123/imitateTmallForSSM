package com.tmall.service.impl;

import com.tmall.dao.OrderItemMapper;
import com.tmall.dao.UserMapper;
import com.tmall.packPojo.OrderItemPack;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.OrderItemExample;
import com.tmall.service.OrderItemService;
import com.tmall.service.OrderService;
import com.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lily_ling on 2017/6/29.
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductService productService;

    @Override
    public int findTotalOrderItemNumber() {
        return orderItemMapper.countByExample(new OrderItemExample());
    }

    @Override
    public OrderItem addOrderItem(OrderItem orderItem) {
        orderItemMapper.insertSelective(orderItem);
        return orderItem;
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKey(orderItem);
    }

    @Override
    public void deleteOrderItem(Integer id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OrderItemPack findOrderItemPackById(Integer id) {
        //查询到OrderItem
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
        if (orderItem == null) return null;
        //设置真正的结果
        OrderItemPack pack = new OrderItemPack();

        fillOrderPack(pack, orderItem);
        return pack;
    }


    @Override
    public List<OrderItemPack> findOrderItemPackByUid(Integer uid) {
        OrderItemExample example = new OrderItemExample();
        //设置查询条件
        example.createCriteria().andUidEqualTo(uid).andOidEqualTo(-1);
        //查询
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        //设置和填充真正的结果集
        List<OrderItemPack> result = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            //防止空指针
            if (orderItem == null) continue;
            OrderItemPack pack = new OrderItemPack();
            //填充pack中各项属性
            fillOrderPack(pack, orderItem);
            result.add(pack);
        }
        return result;
    }

    @Override
    public List<OrderItemPack> findOrderItemPackByPid(Integer pid) {

        OrderItemExample example = new OrderItemExample();
        //设置查询条件
        example.createCriteria().andUidEqualTo(pid);
        //查询
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        //设置和填充真正的结果集
        List<OrderItemPack> result = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            //防止空指针
            if (orderItem == null) continue;

            OrderItemPack pack = new OrderItemPack();
            //填充pack中各项属性
            fillOrderPack(pack, orderItem);

            result.add(pack);
        }
        return result;
    }

    @Override
    public List<OrderItemPack> findOrderItemPackByOid(Integer oid) {

        OrderItemExample example = new OrderItemExample();
        //设置查询条件
        OrderItemExample.Criteria criteria = example.createCriteria();
        criteria.andUidEqualTo(oid);
        //查询
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        //设置和填充真正的结果集
        List<OrderItemPack> result = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            //防止空指针
            if (orderItem == null) continue;
            OrderItemPack pack = new OrderItemPack();
            //填充pack中各项属性
            fillOrderPack(pack, orderItem);
            result.add(pack);
        }
        return result;
    }

    @Override
    public int getProductSaleCount(int pid) {
        int total = 0;
        OrderItemExample example = new OrderItemExample();
        OrderItemExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        List<OrderItem> list = orderItemMapper.selectByExample(example);
        for (OrderItem o : list) {
            total = total + o.getNumber();
        }
        return total;
    }

    @Override
    public void deleteOrdetItemByOrderItemId(Integer oiid) {
        orderItemMapper.deleteByPrimaryKey(oiid);
    }

    //填充pack中各项属性
    private void fillOrderPack(OrderItemPack pack, OrderItem orderItem) {
        pack.setOrderItem(orderItem);

        pack.setUser(userMapper.selectByPrimaryKey(orderItem.getUid()));
        pack.setProduct(productService.selectProductById(orderItem.getPid()));
    }
}
