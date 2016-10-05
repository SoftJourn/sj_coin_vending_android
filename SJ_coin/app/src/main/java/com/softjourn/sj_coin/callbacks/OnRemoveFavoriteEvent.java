package com.softjourn.sj_coin.callbacks;


import com.softjourn.sj_coin.model.products.Product;

public class OnRemoveFavoriteEvent {
    private Product mProduct;

    public OnRemoveFavoriteEvent(Product product) {
        this.mProduct = product;
    }

    public Product removeFavorite() {
        return mProduct;
    }
}

