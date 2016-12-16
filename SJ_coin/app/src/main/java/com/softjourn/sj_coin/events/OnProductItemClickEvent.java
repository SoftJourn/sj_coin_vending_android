package com.softjourn.sj_coin.events;

import com.softjourn.sj_coin.api.models.products.Product;

public class OnProductItemClickEvent {

    private final Product mProduct;

    public OnProductItemClickEvent(Product product){
        this.mProduct = product;
    }

    public Product getProduct(){
        return mProduct;
    }
}
