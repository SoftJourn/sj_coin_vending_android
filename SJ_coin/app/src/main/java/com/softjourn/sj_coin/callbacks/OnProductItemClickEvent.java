package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.products.Product;

public class OnProductItemClickEvent {

    private final Product mProduct;

    public OnProductItemClickEvent(Product product){
        this.mProduct = product;
    }

    public Product getProduct(){
        return mProduct;
    }
}
