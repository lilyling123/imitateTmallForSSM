package com.tmall.comparator;


import com.tmall.packPojo.ProductPack;

import java.util.Comparator;

public class ProductPriceComparator implements Comparator<ProductPack> {

    @Override
    public int compare(ProductPack p1, ProductPack p2) {
        return (int) (p1.getPromotePrice() - p2.getPromotePrice());
    }

}