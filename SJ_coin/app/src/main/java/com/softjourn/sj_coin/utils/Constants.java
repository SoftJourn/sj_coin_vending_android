package com.softjourn.sj_coin.utils;

/**
 * Created by Ad1 on 28.07.2016.
 */
public interface Constants {

    //Preferences Storage Keys
    String SJ_COINS_PREFERENCES = "SJ_COINS_PREFERENCES";
    String ACCESS_TOKEN = "ACCESS_TOKEN";
    String REFRESH_TOKEN = "REFRESH_TOKEN";

    String SELECTED_MACHINE_NAME = "SELECTED_MACHINE_NAME";
    String SELECTED_MACHINE_ID = "SELECTED_MACHINE_ID";
    String SELECTED_MACHINE_ROWS = "SELECTED_MACHINE_ROWS";
    String SELECTED_MACHINE_COLUMNS = "SELECTED_MACHINE_COLUMNS";

    String SELECTED_VIEW = "SELECTED_VIEW";


    //View IDs
    int LIST_VIEW = 1;
    int MACHINE_VIEW = 2;

    //Fragments tags
    String TAG_PRODUCTS_LIST_FRAGMENT = "PRODUCTS_LIST";
    String TAG_PRODUCTS_MACHINE_FRAGMENT = "PRODUCTS_MACHINE";

    String BASE_URL = "https://sjcoins.testing.softjourn.if.ua";

    String URL_AUTH_SERVICE = BASE_URL + "/auth/";
    String URL_VENDING_SERVICE = BASE_URL + "/vending/";
    String URL_COIN_SERVICE = BASE_URL + "/coins/";

    String GRANT_TYPE = "password";

    boolean CALL_FAILED = false;
    boolean CALL_SUCCEED = true;

    /**
     * Headers
     */
    String OAUTH = "OAUTH";
    String VENDING = "VENDING";
    String COINS = "COINS";
}

