package com.tmall.controller;

import com.tmall.packPojo.CategoryPack;
import com.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/28.
 */
@Controller
public class ForeController {
    @Autowired
    private CategoryService categoryService;

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
}
