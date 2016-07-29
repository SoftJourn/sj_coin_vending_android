package com.softjourn.sj_coin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.softjourn.sj_coin.App;

/**
 * Created by Ad1 on 29.07.2016.
 */
public class Preferences implements Constants {

    private static SharedPreferences sharedPreferences = App.getContext().getSharedPreferences(SJ_COINS_PREFERENCES, Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = sharedPreferences.edit();

    public static String retrieveObject(String keyValue) {
        return sharedPreferences.getString(keyValue, null);
    }

    public static void storeString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }
}
