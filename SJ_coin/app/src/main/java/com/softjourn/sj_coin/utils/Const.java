package com.softjourn.sj_coin.utils;

public interface Const {

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
    String TAG_PRODUCTS_LAST_ADDED_FRAGMENT = "TAG_PRODUCTS_LAST_ADDED_FRAGMENT";
    String TAG_PRODUCTS_BEST_SELLERS_FRAGMENT = "TAG_PRODUCTS_BEST_SELLERS_FRAGMENT";
    String TAG_PRODUCTS_SNACKS_FRAGMENT = "TAG_PRODUCTS_SNACKS_FRAGMENT";
    String TAG_PRODUCTS_DRINKS_FRAGMENT = "TAG_PRODUCTS_DRINKS_FRAGMENT";
    String TAG_FAVORITES_FRAGMENT = "TAG_FAVORITES_FRAGMENT";
    String TAG_ALL_PRODUCTS_FRAGMENT = "TAG_ALL_PRODUCTS_FRAGMENT";

    //URLs
    String BASE_URL = "https://sjcoins.testing.softjourn.if.ua";

    String VENDING_API_VERSION = "v1/";

    String URL_AUTH_SERVICE = BASE_URL + "/auth/";
    String URL_VENDING_SERVICE = "https://vending.softjourn.if.ua/"+VENDING_API_VERSION;
    String URL_COIN_SERVICE = BASE_URL + "/coins/";

    String GRANT_TYPE_PASSWORD = "password";
    String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    boolean CALL_FAILED = false;
    boolean CALL_SUCCEED = true;

    boolean TOKEN_REFRESHED = true;
    boolean TOKEN_NOT_REFRESHED = false;

    //Recycler View Types
    String DEFAULT_RECYCLER_VIEW = "DEFAULT";
    String SEE_ALL_SNACKS_DRINKS_RECYCLER_VIEW = "SEE_ALL_SNACKS_DRINKS";

    //See All button Tags
    //are using for correct appearance of SeeAllActivity Label
    String LAST_ADDED = "Last Added";
    String FAVORITES = "Favorites";
    String BEST_SELLERS = "Best Sellers";
    String SNACKS = "Snacks";
    String DRINKS = "Drinks";
    String ALL_ITEMS = "All Items";

    String MACHINE_ID = "3";

    //invalid product, item id
    int INVALID_ID = -1;

    String DB_NAME = "VENDING_DB";

    //Temporary Categories Names while I've not decide what to do with them.
    String DRINK_CATEGORY = "Drink";
    String SNACK_CATEGORY = "Snack";

}

