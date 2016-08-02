package com.softjourn.sj_coin.utils;

/**
 * Created by Ad1 on 01.08.2016.
 */
public class ServerErrors {
    public static String showErrorMessage(int code){
        switch(code){
            case 400:
                return  "The request was malformed.";
            case 404:
                return  "The requested resource did not exist.";
            case 409:
                return "Cant process request due to error like such item already presented or not enough money to buy item.";
            default:
                return "Server error. Please try again later or reinstall the app";
        }
    }
}
