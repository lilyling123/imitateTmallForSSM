package com.tmall.service;

import com.tmall.packPojo.ReviewPack;
import com.tmall.pojo.Review;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/29.
 */
public interface ReviewService {
    void addReview(Review review);

    List<ReviewPack> findReviewPackListByPid(Integer pid);

    int getReviewCountByPid(int pid);
}
