package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.CustomizedProduct;

public class OnAddFavoriteEvent {
    private CustomizedProduct mProduct;

    public OnAddFavoriteEvent(CustomizedProduct product) {
        this.mProduct = product;
    }

    public CustomizedProduct addFavorite(){
        return mProduct;
    }
}
