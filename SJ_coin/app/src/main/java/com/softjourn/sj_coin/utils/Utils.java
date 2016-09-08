package com.softjourn.sj_coin.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.softjourn.sj_coin.model.Session;
import com.softjourn.sj_coin.model.machines.Machines;

import java.util.Date;

public class Utils {

    public static void showErrorToast(Context context, String text, int gravity) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    public static void showErrorToast(Context context, String text) {
        showErrorToast(context, text, Gravity.CENTER);
    }

    public static void storeSessionInfo(Session session) {
        Preferences.storeObject(Constants.ACCESS_TOKEN, session.getAccessToken());
        Preferences.storeObject(Constants.REFRESH_TOKEN, session.getRefreshToken());
        Preferences.storeObject(Constants.EXPIRATION_DATE, String.valueOf((new Date().getTime() / 1000) + Long.parseLong(session.getExpireIn())));
    }

    public static void storeConcreteMachineInfo(Machines machine) {
        Preferences.storeObject(Constants.SELECTED_MACHINE_ROWS, machine.getSize().getRows());
        Preferences.storeObject(Constants.SELECTED_MACHINE_COLUMNS, machine.getSize().getColumns());
        Preferences.storeObject(Constants.SELECTED_MACHINE_ID, machine.getId());
        Preferences.storeObject(Constants.SELECTED_MACHINE_NAME, machine.getName());
    }

    public static void clearUsersData(){
        Preferences.clearStringObject(Constants.ACCESS_TOKEN);
        Preferences.clearStringObject(Constants.REFRESH_TOKEN);
    }
}
