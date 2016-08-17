package com.softjourn.sj_coin.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Utils {
    public static void showErrorToast(Context context, String text, int gravity) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    public static void showErrorToast(Context context, String text) {
        showErrorToast(context, text, Gravity.CENTER);
    }
}
