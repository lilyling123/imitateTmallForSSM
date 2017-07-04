package com.tmall.controller;

import com.tmall.packPojo.OrderItemPack;
import com.tmall.packPojo.OrderPack;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.User;
import com.tmall.service.OrderItemService;
import com.tmall.service.OrderService;
import com.tmall.service.UserService;
import com.tmall.utils.CookieUtils;
import com.tmall.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lily_ling on 2017/7/2.
 */
@Controller
public class CartController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    //展示购物车
    @RequestMapping("/forecart")
    public String forecart(Model model, HttpServletRequest req) {
        User user = getUserByCookie(req);

        List<OrderItemPack> orderitemLists = orderItemService.findOrderItemPackByUid(user.getId());

        model.addAttribute("ois", orderitemLists);

        return "jsp/cart";
    }
    //改变购物车中的商品的数量
    @RequestMapping("/forechangeOrderItem")
    @ResponseBody
    public String foreDeleteOrderItem(Integer pid, Integer number, HttpServletRequest req, Model model) {
        User user = getUserByCookie(req);

        if (null == user)
            return "fail";
        List<OrderItemPack> orderItemPacks = orderItemService.findOrderItemPackByUid(user.getId());

        for (OrderItemPack o : orderItemPacks) {
            if (o.getPid().equals(pid)) {
                o.setNumber(number);
                orderItemService.updateOrderItem(o);
                break;
            }
        }

        return "success";
    }
    //删除购物车中的商品项
    @RequestMapping("/foredeleteOrderItem")
    @ResponseBody
    public String foreChangeOrderItem(Integer oiid, HttpServletRequest req) {
        User user = getUserByCookie(req);

        if (null == user)
            return "fail";
        orderItemService.deleteOrdetItemByOrderItemId(oiid);

        return "success";
    }
    //展示订单列表
    @RequestMapping("/forebought")
    public String foreBought(HttpServletRequest req, Model model) {
        User user = getUserByCookie(req);

        List<OrderPack> orderPacks = orderService.findOrderPackByUId(user.getId());

        model.addAttribute("os", orderPacks);

        return "jsp/bought";
    }
    //删除订单
    @RequestMapping("/foredeleteOrder")
    @ResponseBody
    public String foreDeleteOrder(Integer oid) {
        OrderPack pack = orderService.findOrderPackById(oid);

        orderService.deleteOrderByOrderId(pack);
        return "success";
    }
    //检测是否登录
    @RequestMapping("/forecheckLogin")
    @ResponseBody
    public Result foreCheckLogin(HttpServletRequest req) {
        User u = getUserByCookie(req);
        if (null == u)
            return Result.build(201, "未登录");
        return Result.ok(u);
    }
    //将商品加入到购物车中
    @RequestMapping(value = "foreaddCart")
    @ResponseBody
    public String foreAddCart(Integer pid, Integer num, HttpServletRequest request) {
        //查询用户
        User u = getUserByCookie(request);
        if (u == null)
            return "fail";
        //购物车中如果已经有了，则直接更新数量
        List<OrderItemPack> orderPacks = orderItemService.findOrderItemPackByUid(u.getId());
        for (OrderItemPack orderPack : orderPacks) {
            if (orderPack.getPid().equals(pid)) {
                //找到了商品，增加数量
                orderPack.setNumber(orderPack.getNumber() + num);
                orderItemService.updateOrderItem(orderPack);
                return "success";
            }
        }
        //没有则新建一个然后插入到数据库中
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(-1);
        orderItem.setPid(pid);
        orderItem.setNumber(num);
        orderItem.setUid(u.getId());

        orderItemService.addOrderItem(orderItem);

        return "success";
    }
    //根据Request中的cookie从redis中获取用户
    private User getUserByCookie(HttpServletRequest req) {
        String token = CookieUtils.getCookieValue(req, "token");
        Result result = userService.getUserByToken(token);
        User user = (User) result.getData();
        return user;
    }
}
