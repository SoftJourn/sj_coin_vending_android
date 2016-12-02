package com.softjourn.sj_coin.events;

import com.softjourn.sj_coin.api.models.products.Product;

public class OnRemoveFavoriteEvent {

    private final Product mProduct;

    public OnRemoveFavoriteEvent(Product product) {
        this.mProduct = product;
    }

    public Product removeFavorite() {
        return mProduct;
    }
}

