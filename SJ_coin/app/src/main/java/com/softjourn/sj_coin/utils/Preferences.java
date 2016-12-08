package com.softjourn.sj_coin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.softjourn.sj_coin.App;

public class Preferences implements Const {

    private static final SharedPreferences sharedPreferences = App.getContext().getSharedPreferences(SJ_COINS_PREFERENCES, Context.MODE_PRIVATE);
    private static final SharedPreferences.Editor editor = sharedPreferences.edit();

    public static String retrieveStringObject(String keyValue) {
        return sharedPreferences.getString(keyValue, null);
    }

    public static boolean retrieveBooleanObject(String key) {
        return sharedPreferences.getBoolean(key, true);
    }

    public static void storeBooleanObject(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void storeObject(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static void clearStringObject(String key){
        editor.putString(key, "");
        editor.commit();
    }
}
