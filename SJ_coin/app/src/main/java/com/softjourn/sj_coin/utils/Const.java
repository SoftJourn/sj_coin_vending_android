package com.softjourn.sj_coin.utils;

public interface Const {

    //Preferences Storage Keys
    String SJ_COINS_PREFERENCES = "SJ_COINS_PREFERENCES";
    String ACCESS_TOKEN = "ACCESS_TOKEN";
    String REFRESH_TOKEN = "REFRESH_TOKEN";
    String EXPIRATION_DATE = "EXPIRATION_DATE";

    String SELECTED_MACHINE_NAME = "SELECTED_MACHINE_NAME";
    String SELECTED_MACHINE_ID = "SELECTED_MACHINE_ID";

    String USER_NAME_PREFERENCES_KEY = "USER_NAME_PREFERENCES_KEY";
    String USER_BALANCE_PREFERENCES_KEY = "USER_BALANCE_PREFERENCES_KEY";

    //Fragments tags
    String TAG_PRODUCTS_LAST_ADDED_FRAGMENT = "TAG_PRODUCTS_LAST_ADDED_FRAGMENT";
    String TAG_PRODUCTS_BEST_SELLERS_FRAGMENT = "TAG_PRODUCTS_BEST_SELLERS_FRAGMENT";
    String TAG_FAVORITES_FRAGMENT = "TAG_FAVORITES_FRAGMENT";
    String TAG_ALL_PRODUCTS_FRAGMENT = "TAG_ALL_PRODUCTS_FRAGMENT";

    //URLs
    String BASE_URL = "https://vending.softjourn.if.ua/api";
    //URL for testing server
    //String BASE_URL = "https://sjcoins-testing.softjourn.if.ua";

    //Test URL
    //String BASE_URL = "http://192.168.102.251:8111";

    String VENDING_API_VERSION = "v1/";
    String COINS_API_VERSION = "api/v1/";

    String URL_AUTH_SERVICE = BASE_URL + "/auth/";
    String URL_VENDING_SERVICE = BASE_URL + "/vending/" + VENDING_API_VERSION;
    String URL_COIN_SERVICE = BASE_URL + "/coins/" + COINS_API_VERSION;

    String GRANT_TYPE_PASSWORD = "password";
    String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    boolean CALL_FAILED = false;
    boolean CALL_SUCCEED = true;

    //Recycler View Types
    String DEFAULT_RECYCLER_VIEW = "DEFAULT";
    String SEE_ALL_SNACKS_DRINKS_RECYCLER_VIEW = "SEE_ALL_SNACKS_DRINKS";

    //See All button Tags
    //are using for correct appearance of SeeAllActivity Label
    String LAST_ADDED = "Last Added";
    String FAVORITES = "Favorites";
    String BEST_SELLERS = "Best Sellers";
    String ALL_ITEMS = "All Items";

    //Headers for HTTP
    String HEADER_AUTHORIZATION_KEY = "Authorization";
    String HEADER_AUTHORIZATION_VALUE = "Basic dXNlcl9jcmVkOnN1cGVyc2VjcmV0";

    String HEADER_CONTENT_TYPE_KEY = "Content-Type";
    String HEADER_CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";

    String ACTION_ADD_FAVORITE = "ADD";
    String ACTION_REMOVE_FAVORITE = "REMOVE";

    String IS_FIRST_TIME_LAUNCH = "IS_FIRST_TIME_LAUNCH";
}

