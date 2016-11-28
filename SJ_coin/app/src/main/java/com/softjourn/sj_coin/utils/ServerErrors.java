package com.softjourn.sj_coin.utils;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;


/**
 * Class to handle errors returned from server
 * to appear correct message to user.
 */
public class ServerErrors {
    public static String showErrorMessage(String message) {
        switch (message) {
            case "40401" :
                return App.getContext().getString(R.string.server_error_40401);
            case "40402" :
                return App.getContext().getString(R.string.server_error_40402);
            case "40403" :
                return App.getContext().getString(R.string.server_error_40403);
            case "40404" :
                return App.getContext().getString(R.string.server_error_40404);
            case "40405" :
                return App.getContext().getString(R.string.server_error_40405);
            case "40406" :
                return App.getContext().getString(R.string.server_error_40406);
            case "40407" :
                return App.getContext().getString(R.string.server_error_40407);
            case "40408" :
                return App.getContext().getString(R.string.server_error_40408);
            case "40409" :
                return App.getContext().getString(R.string.server_error_40409);
            case "40410" :
                return App.getContext().getString(R.string.server_error_40410);
            case "40901" :
                return App.getContext().getString(R.string.server_error_40901);
            case "40902" :
                return App.getContext().getString(R.string.server_error_40902);
            case "40903" :
                return App.getContext().getString(R.string.server_error_40903);
            case "50901" :
                return App.getContext().getString(R.string.server_error_50901);
            case "400":
                return App.getContext().getString(R.string.server_error_400);
            case "401":
                return App.getContext().getString(R.string.server_error_401);
            case "404":
                return App.getContext().getString(R.string.server_error_404);
            case "409":
                return App.getContext().getString(R.string.server_error_409);
            case "509" :
                return App.getContext().getString(R.string.server_error_509);
            default:
                return App.getContext().getString(R.string.server_error_other);
        }
    }
}
