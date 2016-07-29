package com.softjourn.sj_coin.utils;

import android.content.Context;
import android.content.Intent;

import com.softjourn.sj_coin.activities.LoginActivity;
import com.softjourn.sj_coin.activities.VendingActivity;

/**
 * Created by Ad1 on 29.07.2016.
 */
public class Navigation implements Constants{

    public static void goToVendingActivity(Context context){
        Intent intent = new Intent(context,VendingActivity.class);
        context.startActivity(intent);
    }

    public static void goToLoginActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
