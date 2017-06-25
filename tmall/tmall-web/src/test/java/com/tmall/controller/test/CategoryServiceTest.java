package com.tmall.controller.test;

import com.tmall.service.CategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lily_ling on 2017/6/24.
 */
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/*.xml");

        System.out.println(context.getBean(CategoryService.class) == null);

    }
}
