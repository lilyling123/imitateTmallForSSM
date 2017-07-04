package com.tmall.service.impl;

import com.tmall.dao.ProductMapper;
import com.tmall.dao.ReviewMapper;
import com.tmall.dao.UserMapper;
import com.tmall.packPojo.ReviewPack;
import com.tmall.pojo.Review;
import com.tmall.pojo.ReviewExample;
import com.tmall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lily_ling on 2017/6/29.
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addReview(Review review) {
        review.setCreateDate(new Date());

        reviewMapper.insert(review);
    }

    @Override
    public List<ReviewPack> findReviewPackListByPid(Integer pid) {
        //设置查询条件
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        List<Review> reviews = reviewMapper.selectByExample(example);

        //设置和填充真正的结果集
        List<ReviewPack> result = new ArrayList<>();
        for (Review review : reviews) {
            ReviewPack pack = new ReviewPack();
            pack.setReview(review);

            pack.setUser(userMapper.selectByPrimaryKey(review.getUid()));
            pack.setProduct(productMapper.selectByPrimaryKey(review.getPid()));

            result.add(pack);
        }

        return result;
    }

    @Override
    public int getReviewCountByPid(int pid) {
        ReviewExample example = new ReviewExample();
        ReviewExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        int total = reviewMapper.countByExample(example);
        return total;
    }
}
