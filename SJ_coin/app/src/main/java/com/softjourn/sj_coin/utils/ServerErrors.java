package com.softjourn.sj_coin.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Ad1 on 01.08.2016.
 */
public class ServerErrors {
    public static void showErrorToast(Context context, int code){
        switch(code){
            case 400:
                Toast.makeText(context,"The request was malformed.",Toast.LENGTH_LONG).show();
                break;
            case 404:
                Toast.makeText(context,"The requested resource did not exist.",Toast.LENGTH_LONG).show();
                break;
            case 409:
                Toast.makeText(context,"Cant process request due to error like such item already presented or not enough money to buy item.", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(context,"Server error. Please try again later or reinstall the app",Toast.LENGTH_LONG).show();
        }
    }
}
