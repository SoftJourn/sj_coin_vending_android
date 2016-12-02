package com.softjourn.sj_coin.events;

import com.softjourn.sj_coin.api.models.products.Product;

public class OnAddFavoriteEvent {
    private final Product mProduct;

    public OnAddFavoriteEvent(Product product) {
        this.mProduct = product;
    }

    public Product addFavorite(){
        return mProduct;
    }
}
