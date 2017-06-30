package com.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.tmall.dao.UserMapper;
import com.tmall.pojo.User;
import com.tmall.pojo.UserExample;
import com.tmall.service.UserService;
import com.tmall.utils.JsonUtils;
import com.tmall.utils.Result;
import com.tmall.utils.jedis.JedisClientPool.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by lily_ling on 2017/6/24.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

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

    @Override
    public Result userLogin(String username, String password) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        if (users == null || users.size() == 0) {
            //返回登录失败
            return Result.build(400, "用户名或密码错误");
        }
        //取用户信息
        User user = users.get(0);
        //2、不正确返回登录失败
        if (!password.equals(user.getPassword())) {
            //返回登录失败
            return Result.build(400, "用户名或密码错误");
        }
        //3、正确生成token
        String token = UUID.randomUUID().toString();

        //4、把用户信息写入redis，key：token value：用户信息
        user.setPassword(null);
        jedisClient.set("SESSION:" + token, JsonUtils.objectToJson(user));

        //5、设置Session的过期时间
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);

        //6、把token返回
        return Result.ok(token);
    }

    @Override
    public Result getUserByToken(String token) {
        //根据token到redis中取用户信息
        String json = jedisClient.get("SESSION:" + token);
        //取不到用户信息，登录已经过期，返回登录过期
        if (StringUtils.isEmpty(json)) {
            return Result.build(201, "用户登录已经过期");
        }
        //取到用户信息更新token的过期时间
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        //返回结果，其中E3Result中包含TbUser对象
        User user = JsonUtils.jsonToPojo(json, User.class);
        return Result.ok(user);
    }



}
