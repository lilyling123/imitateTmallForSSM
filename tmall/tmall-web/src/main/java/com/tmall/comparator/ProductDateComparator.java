package com.tmall.comparator;


import com.tmall.packPojo.ProductPack;

import java.util.Comparator;

public class ProductDateComparator implements Comparator<ProductPack> {

    @Override
    public int compare(ProductPack p1, ProductPack p2) {
        if (p1 == null && p2 == null) return 0;
        if (p1 == null) return -1;
        if (p2 == null) return 1;
        if (p1.getCreateDate() == null && p2.getCreateDate() == null) return 0;
        if (p1.getCreateDate() == null) return -1;
        if (p2.getCreateDate() == null) return 1;
        int i = p1.getCreateDate().after(p2.getCreateDate()) ? 1 : p1.getCreateDate().equals(p2.getCreateDate()) ? 0 : -1;
        return i;
    }

}
