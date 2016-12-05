package com.softjourn.sj_coin.events;

import com.softjourn.sj_coin.api.models.products.Product;

/**
 * Created by omartynets on 05.12.2016.
 */

public class OnProductDetailsClick {

    private final Product mProduct;

    public OnProductDetailsClick(Product product) {
        this.mProduct = product;
    }

    public Product getProduct() {
        return mProduct;
    }
}
