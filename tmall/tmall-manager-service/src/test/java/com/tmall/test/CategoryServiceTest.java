package com.tmall.test;

import com.tmall.packPojo.CategoryPack;
import com.tmall.service.CategoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryServiceImpl;

    @Test
    public void testSelectCategoryById() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/*.xml");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CategoryService bean = context.getBean(CategoryService.class);

        List<CategoryPack> packList = bean.findCategoryPackList();

        System.out.println();

    }
}
