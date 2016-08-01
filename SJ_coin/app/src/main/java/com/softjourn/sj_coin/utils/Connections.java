package com.softjourn.sj_coin.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.softjourn.sj_coin.App;

/**
 * Created by Ad1 on 29.07.2016.
 */
public class Connections {
    public static boolean isNetworkEnabled() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        return info != null && info.isConnected();
    }
}

