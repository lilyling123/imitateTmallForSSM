package com.tmall.service;

import com.tmall.packPojo.ReviewPack;

import java.util.List;

/**
 * Created by lily_ling on 2017/6/29.
 */
public interface ReviewService {
    List<ReviewPack> findReviewPackListByPid(Integer pid, int startPage, int count);

    int getReviewCountByPid(int pid);
}
