package com.softjourn.sj_coin.callbacks;


import com.softjourn.sj_coin.model.products.Product;

import java.util.List;

public class OnRemovedLastFavoriteEvent {

    private final List<Product> mList;

    public OnRemovedLastFavoriteEvent(List<Product> productList){
        this.mList = productList;
    }

    public List<Product> getList(){
        return mList;
    }
}

