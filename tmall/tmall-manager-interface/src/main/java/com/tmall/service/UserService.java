package com.tmall.service;

import com.tmall.pojo.User;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/24.
 */
public interface UserService {
    List<User> findUserList(Integer startPage, Integer rows);

    Integer findUserTotalNumber();

    User findUserByUserName(String username);

    void addUser(User user);
}
