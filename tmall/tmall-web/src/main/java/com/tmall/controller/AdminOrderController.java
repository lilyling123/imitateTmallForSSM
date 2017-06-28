package com.tmall.controller;

import com.tmall.packPojo.OrderPack;
import com.tmall.service.OrderService;
import com.tmall.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/28.
 */
@Controller
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    //进入订单列表
    @RequestMapping("/admin_order_list")
    public String findOrders(@RequestParam(value = "page.start", defaultValue = "0") Integer start,
                             @RequestParam(defaultValue = "5") Integer count,
                             Model model) {
        Page page = new Page(start, count);
        int startPage = start / count + 1;

        List<OrderPack> res = orderService.findOrderPacks(startPage, count);
        int total = orderService.findOrdersNumber();
        page.setTotal(total);

        model.addAttribute("os", res);
        model.addAttribute("page", page);

        return "admin/listOrder";
    }
}
