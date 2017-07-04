package com.tmall.controller;

import com.tmall.packPojo.OrderItemPack;
import com.tmall.packPojo.OrderPack;
import com.tmall.service.OrderItemService;
import com.tmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by lily_ling on 2017/7/2.
 */
@Controller
public class PayController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    //来到付款页面
    @RequestMapping("/forealipay")
    public String foreAlipay(Integer oid, Float total, Model model) {
        //数据回显和返回视图
        model.addAttribute("oid", oid);

        model.addAttribute("total", total);

        return "jsp/alipay";
    }
    //已付款
    @RequestMapping("/forepayed")
    public String forePayed(Integer oid, float total, HttpServletRequest req) {
        //查询商品数据
        OrderPack pack = orderService.findOrderPackById(oid);
        //设置值
        pack.setStatus(OrderService.waitDelivery);
        pack.setPayDate(new Date());
        orderService.updateOrder(pack);
        //数据回显和返回视图
        req.setAttribute("o", pack);
        return "jsp/payed";
    }
    //确认支付
    @RequestMapping("/foreconfirmPay")
    public String foreconfirmPay(Integer oid, Integer total, HttpServletRequest req) {
        //查询商品数据
        OrderPack o = orderService.findOrderPackById(oid);

        List<OrderItemPack> ois = orderItemService.findOrderItemPackByOid(o.getId());
        //设置值
        float Ototal = 0;
        for (OrderItemPack oi : ois) {
            Ototal += oi.getNumber() * oi.getProduct().getPromotePrice();
        }
        o.setTotal(Ototal);
        o.setOrderItems(ois);
        //数据回显和返回视图
        req.setAttribute("o", o);
        return "jsp/confirmPay";
    }
}
