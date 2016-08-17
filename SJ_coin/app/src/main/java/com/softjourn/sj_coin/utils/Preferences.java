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

    public static String retrieveStringObject(String keyValue) {
        return sharedPreferences.getString(keyValue, null);
    }

    public static int retrieveIntObject(String keyValue) {
        return sharedPreferences.getInt(keyValue, 1);
    }

    public static void storeObject(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public static void storeObject(String key, int value){
        editor.putInt(key,value);
        editor.apply();
    }

    public static void storeObject(String key, long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    public static void clearIntObject(String key){
        editor.putInt(key,0);
        editor.apply();
    }

    public static void clearStringObject(String key){
        editor.putString(key,null);
        editor.apply();
    }
}
