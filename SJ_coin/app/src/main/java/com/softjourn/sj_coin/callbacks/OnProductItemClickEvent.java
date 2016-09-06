package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.CustomizedProduct;

public class OnProductItemClickEvent {
    private CustomizedProduct mProduct;

    public OnProductItemClickEvent(CustomizedProduct product){
        this.mProduct = product;
    }

    public CustomizedProduct getProduct(){
        return mProduct;
    }
}
