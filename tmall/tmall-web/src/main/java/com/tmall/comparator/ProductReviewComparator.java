package com.tmall.comparator;


import com.tmall.packPojo.ProductPack;

import java.util.Comparator;

public class ProductReviewComparator implements Comparator<ProductPack> {

    @Override
    public int compare(ProductPack p1, ProductPack p2) {
        return p2.getReviewCount() - p1.getReviewCount();
    }

}

