package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.products.Products;

public class OnFeaturedProductsListReceived {

    private Products mProduct;

    public OnFeaturedProductsListReceived(Products product) {
        this.mProduct = product;
    }

    public Products getProductsList(){
        return mProduct;
    }
}


