package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.products.Product;

public class OnAddFavoriteEvent {
    private final Product mProduct;

    public OnAddFavoriteEvent(Product product) {
        this.mProduct = product;
    }

    public Product addFavorite(){
        return mProduct;
    }
}
