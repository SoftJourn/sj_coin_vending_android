package com.softjourn.sj_coin;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ad1 on 29.07.2016.
 */
public class App extends Application {
    private static Context instance;

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this.getApplicationContext();
    }
}
