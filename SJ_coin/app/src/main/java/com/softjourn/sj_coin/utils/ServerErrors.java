package com.softjourn.sj_coin.utils;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;

public class ServerErrors {
    public static String showErrorMessage(int code){
        switch(code){
            case 400:
                return App.getContext().getString(R.string.server_error_400);
            case 404:
                return  App.getContext().getString(R.string.server_error_404);
            case 409:
                return App.getContext().getString(R.string.server_error_409);
            default:
                return App.getContext().getString(R.string.server_error_other);
        }
    }
}
