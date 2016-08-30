package com.softjourn.sj_coin.utils;

public interface Constants {

    //Preferences Storage Keys
    String SJ_COINS_PREFERENCES = "SJ_COINS_PREFERENCES";
    String ACCESS_TOKEN = "ACCESS_TOKEN";
    String REFRESH_TOKEN = "REFRESH_TOKEN";
    String EXPIRATION_DATE = "EXPIRATION_DATE";

    String SELECTED_MACHINE_NAME = "SELECTED_MACHINE_NAME";
    String SELECTED_MACHINE_ID = "SELECTED_MACHINE_ID";
    String SELECTED_MACHINE_ROWS = "SELECTED_MACHINE_ROWS";
    String SELECTED_MACHINE_COLUMNS = "SELECTED_MACHINE_COLUMNS";

    //Fragments tags
    String TAG_PRODUCTS_LAST_PURCHASES_FRAGMENT = "TAG_PRODUCTS_LAST_PURCHASES_FRAGMENT";
    String TAG_PRODUCTS_NEW_PRODUCT_FRAGMENT = "TAG_PRODUCTS_NEW_PRODUCT_FRAGMENT";
    String TAG_PRODUCTS_BEST_SELLERS_FRAGMENT = "TAG_PRODUCTS_BEST_SELLERS_FRAGMENT";
    String TAG_PRODUCTS_SNACKS_FRAGMENT = "TAG_PRODUCTS_SNACKS_FRAGMENT";
    String TAG_PRODUCTS_DRINKS_FRAGMENT = "TAG_PRODUCTS_DRINKS_FRAGMENT";
    String TAG_PRODUCTS_SEE_ALL_SNACKS_FRAGMENT = "TAG_PRODUCTS_SEE_ALL_SNACKS_FRAGMENT";
    String TAG_PRODUCTS_SEE_ALL_DRINKS_FRAGMENT = "TAG_PRODUCTS_SEE_ALL_DRINKS_FRAGMENT";
    String TAG_PRODUCTS_SEE_ALL_FRAGMENT = "TAG_PRODUCTS_SEE_ALL_FRAGMENT";

    //URLs
    String BASE_URL = "https://sjcoins.testing.softjourn.if.ua";

    String URL_AUTH_SERVICE = BASE_URL + "/auth/";
    String URL_VENDING_SERVICE = BASE_URL + "/vending/";
    String URL_COIN_SERVICE = BASE_URL + "/coins/";

    String GRANT_TYPE_PASSWORD = "password";
    String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    boolean CALL_FAILED = false;
    boolean CALL_SUCCEED = true;

    boolean TOKEN_REFRESHED = true;
    boolean TOKEN_NOT_REFRESHED = false;

    //Recycler View Types
    String DEFAULT_RECYCLER_VIEW = "DEFAULT";
    String SEE_ALL_RECYCLER_VIEW = "SEE_ALL";
    String SEE_ALL_SNACKS_DRINKS_RECYCLER_VIEW = "SEE_ALL_SNACKS_DRINKS";

    //See All button Tags
    //are using for correct appearance of SeeAllActivity Label
    String NEW_PRODUCTS = "New Products";
    String LAST_PURCHASES = "Last Purchases";
    String BEST_SELLERS = "Best Sellers";
    String SNACKS = "Snacks";
    String DRINKS = "Drinks";

    String MACHINE_ID = "1";
}

