package com.tmall.packPojo;

import com.tmall.pojo.Product;
import com.tmall.pojo.Review;
import com.tmall.pojo.User;

/**
 * Created by lily_ling on 2017/6/24.
 */
public class ReviewPack extends Review {

    private User user;
    private Product product;
    private Review review;

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        if (review != null) {
            this.setId(review.getId());
            this.setPid(review.getPid());
            this.setContent(review.getContent());
            this.setCreateDate(review.getCreateDate());
            this.setUid(review.getUid());
        }
        this.review = review;
    }

    public ReviewPack() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
