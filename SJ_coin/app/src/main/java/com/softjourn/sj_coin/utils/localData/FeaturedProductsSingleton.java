package com.softjourn.sj_coin.utils.localData;


import com.softjourn.sj_coin.model.products.Products;

public class FeaturedProductsSingleton {

    private static FeaturedProductsSingleton sOurInstance = new FeaturedProductsSingleton();

    private Products mProductsList;

    private FeaturedProductsSingleton() {
    }

    public static FeaturedProductsSingleton getInstance() {
        return sOurInstance;
    }

    public void setData(Products products){
        this.mProductsList = products;
    }

    public Products getData(){
        return mProductsList;
    }

    public void onDestroy(){
        mProductsList = null;
    }
}
