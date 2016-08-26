package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.products.Product;

public class OnAddFavoriteEvent {
    private Product mProduct;

    public OnAddFavoriteEvent(Product product) {
        this.mProduct = product;
    }

    public Product addFavorite(){
        return mProduct;
    }
}
