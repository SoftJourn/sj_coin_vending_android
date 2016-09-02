package com.softjourn.sj_coin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.softjourn.sj_coin.App;

public class Preferences implements Constants {

    private static SharedPreferences sharedPreferences = App.getContext().getSharedPreferences(SJ_COINS_PREFERENCES, Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = sharedPreferences.edit();

    public static String retrieveStringObject(String keyValue) {
        return sharedPreferences.getString(keyValue, null);
    }

    public static int retrieveIntObject(String keyValue) {
        return sharedPreferences.getInt(keyValue, 0);
    }

    public static void storeObject(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public static void storeObject(String key, int value){
        editor.putInt(key,value);
        editor.apply();
    }

    public static void clearIntObject(String key){
        editor.remove(key);
    }

    public static void clearStringObject(String key){
        editor.putString(key, "");
        editor.apply();
    }
}
