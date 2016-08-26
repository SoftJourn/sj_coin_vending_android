package com.softjourn.sj_coin.utils;

import android.content.Context;
import android.util.Base64;
import android.view.Gravity;
import android.widget.Toast;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.model.Session;
import com.softjourn.sj_coin.model.machines.Machines;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class Utils {
    public static void showErrorToast(Context context, String text, int gravity) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
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

    public static String getUserNameFromToken() {
        String source = "";
        try {
            source = Preferences.retrieveStringObject(Constants.ACCESS_TOKEN).replaceFirst("[a-zA-Z,0-9]*\\.", "").replaceFirst("\\.[a-zA-Z,0-9]*", "");
        } catch (IllegalArgumentException e) {
            showErrorToast(App.getContext(),App.getContext().getString(R.string.error_unexpected));
        }
        byte[] data = Base64.decode(source, Base64.NO_WRAP);
        String decodedString = null;
        try {
            decodedString = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Work with decoded String
        if (decodedString != null) {
            int tempStart = decodedString.indexOf(App.getContext().getString(R.string.activity_profile_username_key));
            String startOfUserName = decodedString.substring(tempStart + 12);
            int endOfUserName = startOfUserName.indexOf("\"");
            return startOfUserName.substring(0, endOfUserName);
        } else {
            return "";
        }
    }
}
