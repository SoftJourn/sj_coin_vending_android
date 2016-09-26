package com.softjourn.sj_coin;

import android.app.Application;
import android.content.Context;

import com.softjourn.sj_coin.utils.localData.ProductsListSingleton;

public class App extends Application {

    private static Context sInstance;

    public static Context getContext() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this.getApplicationContext();
    }
}
