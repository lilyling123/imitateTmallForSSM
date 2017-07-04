package com.tmall.controller;

import com.tmall.packPojo.OrderPack;
import com.tmall.packPojo.ProductPack;
import com.tmall.packPojo.ReviewPack;
import com.tmall.pojo.Order;
import com.tmall.pojo.Product;
import com.tmall.pojo.Review;
import com.tmall.pojo.User;
import com.tmall.service.*;
import com.tmall.utils.CookieUtils;
import com.tmall.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lily_ling on 2017/7/4.
 */
@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserService userService;
    //返回评论
    @RequestMapping("/forereview")
    public String foreReview(Integer oid, HttpServletRequest req) {
        OrderPack order = orderService.findOrderPackById(oid);

        ProductPack p = productService.selectProductById(order.getOrderItems().get(0).getPid());
        int reviewCount = reviewService.getReviewCountByPid(p.getId());
        int saleCount = orderItemService.getProductSaleCount(p.getId());
        p.setSaleCount(saleCount);
        p.setReviewCount(reviewCount);

        List<ReviewPack> reviews = reviewService.findReviewPackListByPid(p.getId());
        req.setAttribute("p", p);
        req.setAttribute("o", order);
        req.setAttribute("reviews", reviews);
        return "jsp/review";
    }
    //提交评论
    @RequestMapping("/foredoreview")
    public String foredoreview(Review review, Integer oid, HttpServletRequest req) {
        Order order = orderService.findOrderPackById(oid);
        order.setStatus(OrderService.finish);
        orderService.updateOrder(order);


        Product p = productService.selectProductById(review.getPid());

        String content = review.getContent();

        User user = getUserByCookie(req);

        review.setContent(content);

        review.setUid(user.getId());

        reviewService.addReview(review);

        return "redirect:/forereview?oid=" + oid + "&showonly=true";
    }

    private User getUserByCookie(HttpServletRequest req) {
        String token = CookieUtils.getCookieValue(req, "token");
        Result result = userService.getUserByToken(token);
        User user = (User) result.getData();
        return user;
    }
}
