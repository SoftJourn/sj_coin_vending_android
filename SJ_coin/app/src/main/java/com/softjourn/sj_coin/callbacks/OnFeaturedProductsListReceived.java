package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.products.Featured;

public class OnFeaturedProductsListReceived {

    private Featured mProduct;

    public OnFeaturedProductsListReceived(Featured product) {
        this.mProduct = product;
    }

    public Featured getProductsList(){
        return mProduct;
    }
}


