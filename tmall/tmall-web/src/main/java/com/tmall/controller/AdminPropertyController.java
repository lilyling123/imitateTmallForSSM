package com.tmall.controller;

import com.tmall.packPojo.PropertyPack;
import com.tmall.pojo.Property;
import com.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lily_ling on 2017/6/28.
 */
@Controller
public class AdminPropertyController {
    @Autowired
    private PropertyService propertyService;
    //展示商品属性
    @RequestMapping("/admin_property_add")
    public String insertProperty(Property p) {

        propertyService.addProperty(p);
        return "redirect:/admin_property_list?cid=" + p.getCid();
    }
    //编辑商品属性页面
    @RequestMapping("/admin_property_edit")
    public String editProperty(Integer id, Model model) {
        PropertyPack p = propertyService.findPropertyListById(id);

        model.addAttribute("p", p);
        return "admin/editProperty";
    }
    //ajax更新商品属性
    @RequestMapping("/admin_property_update")
    public String updateProperty(Property p) {
        propertyService.updateProperty(p);

        return "redirect:/admin_property_list?cid=" + p.getCid();

    }
    //删除商品属性
    @RequestMapping("/admin_property_delete")
    public String deleteProperty(Integer id) {
        PropertyPack p = propertyService.findPropertyListById(id);
        propertyService.deleteProperty(id);
        return "redirect:/admin_property_list?cid=" + p.getCid();
    }

}
