package com.softjourn.sj_coin.utils.localData;


import com.softjourn.sj_coin.model.products.Products;

public class FeaturedProductsSingleton {

    private static FeaturedProductsSingleton ourInstance = new FeaturedProductsSingleton();

    private Products sProductsList;

    private FeaturedProductsSingleton() {
    }

    public static FeaturedProductsSingleton getInstance() {
        return ourInstance;
    }

    public void setData(Products products){
        this.sProductsList = products;
    }

    public Products getData(){
        return sProductsList;
    }
}
