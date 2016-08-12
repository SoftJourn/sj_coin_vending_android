package com.softjourn.sj_coin.utils;

public interface Constants {

    //Preferences Storage Keys
    String SJ_COINS_PREFERENCES = "SJ_COINS_PREFERENCES";
    String ACCESS_TOKEN = "ACCESS_TOKEN";
    String REFRESH_TOKEN = "REFRESH_TOKEN";

    String SELECTED_MACHINE_NAME = "SELECTED_MACHINE_NAME";
    String SELECTED_MACHINE_ID = "SELECTED_MACHINE_ID";
    String SELECTED_MACHINE_ROWS = "SELECTED_MACHINE_ROWS";
    String SELECTED_MACHINE_COLUMNS = "SELECTED_MACHINE_COLUMNS";

    //Fragments tags
    String TAG_PRODUCTS_LAST_PURCHASES_FRAGMENT = "TAG_PRODUCTS_LAST_PURCHASES_FRAGMENT";
    String TAG_PRODUCTS_FEATURED_FRAGMENT = "TAG_PRODUCTS_FEATURED_FRAGMENT";
    String TAG_PRODUCTS_BEST_SELLERS_FRAGMENT = "TAG_PRODUCTS_BEST_SELLERS_FRAGMENT";
    String TAG_PRODUCTS_SEE_ALL_LAST_PURCHASES_FRAGMENT = "TAG_PRODUCTS_SEE_ALL_LAST_PURCHASES_FRAGMENT";

    //URLs
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

    //Recycler View Types
    String DEFAULT_RECYCLER_VIEW = "DEFAULT";
    String SEE_ALL_RECYCLER_VIEW = "SEE_ALL";

    String LAST_PURCHASES = "Last purchases";
    String FEATURED = "Featured";
    String BEST_SELLERS = "Best Sellers";
}

