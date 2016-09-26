package com.softjourn.sj_coin.utils.localData;


import com.softjourn.sj_coin.model.products.Products;

public class FeaturedProductsSingleton {

    private static FeaturedProductsSingleton sOurInstance;

    private Products mProductsList;

    public static FeaturedProductsSingleton getInstance() {
        if (sOurInstance == null) {
            sOurInstance = new FeaturedProductsSingleton();
        }
        return sOurInstance;
    }

    public void setData(Products products) {
        this.mProductsList = products;
    }

    public Products getData() {
        return mProductsList == null ? new Products() : mProductsList;
    }

    public void onDestroy() {
        mProductsList = null;
    }
}
