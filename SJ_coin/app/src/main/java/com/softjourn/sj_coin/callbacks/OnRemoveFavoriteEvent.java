package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.CustomizedProduct;


public class OnRemoveFavoriteEvent {
    private CustomizedProduct mProduct;

    public OnRemoveFavoriteEvent(CustomizedProduct product) {
        this.mProduct = product;
    }

    public CustomizedProduct removeFavorite() {
        return mProduct;
    }
}

