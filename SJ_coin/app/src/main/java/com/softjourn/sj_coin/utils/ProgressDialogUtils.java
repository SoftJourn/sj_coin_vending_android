package com.softjourn.sj_coin.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.softjourn.sj_coin.R;

public class ProgressDialogUtils {

    private static ProgressDialog mProgressDialog;

    public static void showDialog(Context context, String message) {
            mProgressDialog = new ProgressDialog(context, R.style.Base_V7_Theme_AppCompat_Dialog);
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
    }

    public static void dismiss() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
