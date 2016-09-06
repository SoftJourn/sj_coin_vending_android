package com.softjourn.sj_coin;

import android.app.Application;
import android.content.Context;

import com.softjourn.sj_coin.utils.localData.ProductsListSingleton;

public class App extends Application {
    private static Context instance;

    ProductsListSingleton productsListSingleton;

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        productsListSingleton = ProductsListSingleton.getInstance();
        instance = this.getApplicationContext();
    }
}
