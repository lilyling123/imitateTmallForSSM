package com.tmall.controller;

import com.tmall.pojo.User;
import com.tmall.service.UserService;
import com.tmall.utils.CookieUtils;
import com.tmall.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lily_ling on 2017/6/28.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;
    //前往登录页面
    @RequestMapping(value = "/loginPage")
    public String login() {

        return "jsp/login";
    }
    //登录请求
    @RequestMapping(value = "/forelogin", method = RequestMethod.POST)
    @ResponseBody
    public Result login(User user, HttpServletRequest req, HttpServletResponse resp) {
        if (null == user) {
            return Result.build(400, "登录错误");
        }
        Result result = userService.userLogin(user.getName(), user.getPassword());

        //判断是否登录成功
        if (result.getStatus() == 200) {
            String token = result.getData().toString();
            //登录成功把token写入cookie
            CookieUtils.setCookie(req, resp, TOKEN_KEY, token, 1800);
        }
        //返回结果
        return result;
    }
    //注销登录
    @RequestMapping("/forelogout")
    public String login(HttpServletRequest req, HttpServletResponse resp) {
        CookieUtils.deleteCookie(req, resp, "token");
        return "redirect:/forehome";
    }

    //来到注册页面
    @RequestMapping("/registerPage")
    public String redirectToRegister() {

        return "jsp/register";
    }
    //注册请求
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
