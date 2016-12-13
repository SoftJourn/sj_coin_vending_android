package com.softjourn.sj_coin.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.softjourn.sj_coin.api.models.Session;
import com.softjourn.sj_coin.api.models.machines.Machines;

import java.util.Date;

public class Utils {

    public static void showErrorToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        if (toast.getView().getWindowVisibility() != View.VISIBLE) {
            toast.show();
        }
    }

    public static void showSnackBar(View view, String message){
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void storeSessionInfo(Session session) {
        Preferences.storeObject(Const.ACCESS_TOKEN, session.getAccessToken());
        Preferences.storeObject(Const.REFRESH_TOKEN, session.getRefreshToken());
        Preferences.storeObject(Const.EXPIRATION_DATE, String.valueOf((new Date().getTime() / 1000) + Long.parseLong(session.getExpireIn())));
    }

    public static void storeConcreteMachineInfo(Machines machine) {
        Preferences.storeObject(Const.SELECTED_MACHINE_ID, String.valueOf(machine.getId()));
        Preferences.storeObject(Const.SELECTED_MACHINE_NAME, machine.getName());
    }

    public static void clearUsersData(){
        Preferences.clearStringObject(Const.ACCESS_TOKEN);
        Preferences.clearStringObject(Const.REFRESH_TOKEN);
        Preferences.clearStringObject(Const.SELECTED_MACHINE_NAME);
        Preferences.clearStringObject(Const.SELECTED_MACHINE_ID);
        Preferences.clearStringObject(Const.USER_BALANCE_PREFERENCES_KEY);
        Preferences.clearStringObject(Const.USER_NAME_PREFERENCES_KEY);
    }

    /**
     * @return true if Token have expired
     */
    public static boolean checkExpirationDate() {
        return (new Date().getTime() / 1000 >= Long.parseLong(Preferences.retrieveStringObject(Const.EXPIRATION_DATE)));
    }
}
