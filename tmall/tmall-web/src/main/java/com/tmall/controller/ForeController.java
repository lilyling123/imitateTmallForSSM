package com.tmall.controller;

import com.tmall.comparator.*;
import com.tmall.packPojo.*;
import com.tmall.pojo.User;
import com.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * Created by lily_ling on 2017/6/28.
 */
@Controller
public class ForeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private OrderItemService orderItemService;

    @RequestMapping("/index")
    public String showIndex() {
        return "redirect:/forehome";
    }

    @RequestMapping("/forehome")
    public String redirectToForeHome(Model model) {

        List<CategoryPack> list = categoryService.findCategoryPackList();
        model.addAttribute("cs", list);
        return "jsp/home";
    }

    @RequestMapping("/foreproduct")
    public String findForeProduct(Integer pid, Model model) {

        //查询数据
        ProductPack p = productService.selectProductById(pid);

        List<PropertyValuePack> pvs = propertyValueService.findPropertyValueList(pid);
        List<ReviewPack> reviews = reviewService.findReviewPackListByPid(pid, 0, Short.MAX_VALUE);

        p.setCategory(categoryService.selectCategoryById(p.getCid()));

        List<ProductImagePack> productSingleImages = productImageService.findProductImageByPid(pid, ProductImageService.type_single, 1, Short.MAX_VALUE);

        List<ProductImagePack> productDetailImages = productImageService.findProductImageByPid(pid, ProductImageService.type_detail, 1, Short.MAX_VALUE);
        int reviewCount = reviewService.getReviewCountByPid(p.getId());
        int saleCount = orderItemService.getProductSaleCount(p.getId());

        //组装数据
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);
        p.setSaleCount(saleCount);
        p.setReviewCount(reviewCount);


        //放到model中
        model.addAttribute("reviews", reviews);
        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
        return "jsp/product";
    }

    @RequestMapping("/forecategory")
    public String findForeCategory(Integer cid, Model model, @RequestParam(required = false) String sort) {
        CategoryPack c = categoryService.selectCategoryById(cid);
        List<ProductPack> ps = productService.findProductListByCid(c.getId(), 0, Short.MAX_VALUE);
        c.setProducts(ps);
        for (ProductPack p : ps) {
            int reviewCount = reviewService.getReviewCountByPid(p.getId());
            int saleCount = orderItemService.getProductSaleCount(p.getId());
            p.setSaleCount(saleCount);
            p.setReviewCount(reviewCount);
        }
        if (null != sort) {
            switch (sort) {
                case "review":
                    Collections.sort(c.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(c.getProducts(), new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(c.getProducts(), new ProductSaleCountComparator());
                    break;
                case "price":
                    Collections.sort(c.getProducts(), new ProductPriceComparator());
                    break;
                case "all":
                    Collections.sort(c.getProducts(), new ProductAllComparator());
                    break;
            }
        }

        model.addAttribute("c", c);
        return "jsp/category";
    }


}
