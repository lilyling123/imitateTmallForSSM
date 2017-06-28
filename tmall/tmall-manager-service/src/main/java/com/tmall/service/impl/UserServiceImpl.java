package com.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.tmall.dao.UserMapper;
import com.tmall.pojo.User;
import com.tmall.pojo.UserExample;
import com.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findUserList(Integer startPage, Integer rows) {
        //设置分页
        PageHelper.startPage(startPage, rows);
        //设置查询条件
        UserExample example = new UserExample();

        //执行查询
        List<User> list = userMapper.selectByExample(example);
        return list;
    }

    @Override
    public Integer findUserTotalNumber() {
        //设置查询条件
        UserExample example = new UserExample();
        return userMapper.countByExample(example);
    }

    @Override
    public User findUserByUserName(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);

        if (users != null && users.size() > 0)
            return users.get(0);
        return null;
    }

    @Override
    public void addUser(User user) {
        userMapper.insertSelective(user);

    }
}
