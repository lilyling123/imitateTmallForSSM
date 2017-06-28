package com.tmall.controller;

import com.tmall.pojo.User;
import com.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lily_ling on 2017/6/28.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/loginPage")
    public String login() {

        return "jsp/login";
    }

    @RequestMapping("/forelogin")
    public String login(User user, HttpServletRequest req) {
        User u = userService.findUserByUserName(user.getName());
        //用户不存在
        if (null == user) {
            return "redirect:/loginPage";
        }
        //密码错误
        if (!u.getPassword().equals(user.getPassword())) {
            return "redirect:/loginPage";
        }
        req.getSession().setAttribute("user", u);
        return "redirect:/forehome";
    }

    @RequestMapping("/foreloginAjax")
    @ResponseBody
    public String foreLoginAjax(User user, HttpServletRequest req) {
        User u = userService.findUserByUserName(user.getName());
        //用户不存在
        if (null == user) {
            return "fail";
        }
        //密码错误
        if (!u.getPassword().equals(user.getPassword())) {
            return "fail";
        }
        req.getSession().setAttribute("user", u);
        return "success";
    }

    @RequestMapping("/forelogout")
    public String login(HttpServletRequest req) {
        req.getSession().removeAttribute("user");
        return "redirect:/forehome";
    }


    @RequestMapping("/registerPage")
    public String redirectToRegister() {

        return "jsp/register";
    }

    @RequestMapping("/foreregister")
    public String foreRegister(User user, HttpServletRequest request) {
        User u = userService.findUserByUserName(user.getName());
        //用户名已经存在
        if (u != null) {
            return "redirect:/registerPage";
        }
        userService.addUser(user);
        request.getSession().setAttribute("user", user);
        return "jsp/registerSuccess";
    }
}
