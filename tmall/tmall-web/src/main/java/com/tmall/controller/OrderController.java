package com.tmall.controller;

import com.tmall.packPojo.OrderItemPack;
import com.tmall.packPojo.OrderPack;
import com.tmall.packPojo.ProductPack;
import com.tmall.pojo.Order;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.User;
import com.tmall.service.OrderItemService;
import com.tmall.service.OrderService;
import com.tmall.service.ProductService;
import com.tmall.service.UserService;
import com.tmall.utils.CookieUtils;
import com.tmall.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lily_ling on 2017/7/2.
 */
@Controller
public class OrderController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    //直接购买
    @RequestMapping("/forebuy")
    public String buy(HttpServletRequest req) {

        String[] oiids = req.getParameterValues("oiid");
        List<OrderItemPack> ois = new ArrayList<>();
        float total = 0;
        for (String strid : oiids) {
            int oiid = Integer.parseInt(strid);
            OrderItemPack oi = orderItemService.findOrderItemPackById(oiid);

            total += oi.getProduct().getPromotePrice() * oi.getNumber();
            ois.add(oi);
        }

        req.setAttribute("ois", ois);
        req.setAttribute("total", total);
        return "jsp/buy";
    }
    //创建订单
    @RequestMapping(value = "/forecreateOrder", method = RequestMethod.POST)
    public String foreCreateOrder(Order order, HttpServletRequest req) {

        User user = getUserByCookie(req);

        order.setUid(user.getId());
        order = orderService.addOrder(order);
        float total = 0;

        //计算总价
        List<OrderItemPack> ois = (List<OrderItemPack>) req.getSession().getAttribute("ois");
        if (ois != null && ois.size() > 0) {
            for (OrderItemPack oi : ois) {
                OrderPack pack = new OrderPack();
                pack.setOrder(order);
                oi.setOrder(pack);
                oi.setUid(user.getId());
                oi.setOid(order.getId());
                orderItemService.updateOrderItem(oi);
                total += oi.getProduct().getPromotePrice() * oi.getNumber();
            }
        }

        return "redirect:/forealipay?oid=" + order.getId() + "&total=" + total;
    }

    //购买一个
    @RequestMapping("/forebuyone")
    public String foreBuyOne(Integer pid, Integer num, HttpServletRequest req) {
        //查询商品数据
        int oiid = 0;
        ProductPack p = productService.selectProductById(pid);

        User u = getUserByCookie(req);

        boolean found = false;
        List<OrderItemPack> ois = orderItemService.findOrderItemPackByUid(u.getId());
        //遍历用户订单，查看此商品是否已经在订单中
        for (OrderItemPack oi : ois) {
            if (oi.getProduct().getId() == p.getId()) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.updateOrderItem(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }
        //如果不在订单中，则插入到订单中
        if (!found) {
            OrderItemPack oi = new OrderItemPack();
            OrderItem orderItem = new OrderItem();

            orderItem.setOid(-1);
            orderItem.setPid(p.getId());
            orderItem.setUid(u.getId());
            orderItem.setNumber(num);

            orderItem = orderItemService.addOrderItem(orderItem);

            oi.setOrderItem(orderItem);
            oi.setUser(u);
            oi.setNumber(num);
            oi.setProduct(p);
            oiid = oi.getId();
        }

        return "redirect:/buy?oiid=" + oiid;
    }

    private User getUserByCookie(HttpServletRequest req) {
        String token = CookieUtils.getCookieValue(req, "token");
        Result result = userService.getUserByToken(token);
        User user = (User) result.getData();
        return user;
    }
}
