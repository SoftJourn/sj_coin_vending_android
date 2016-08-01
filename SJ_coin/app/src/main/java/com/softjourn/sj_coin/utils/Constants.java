package com.softjourn.sj_coin.utils;

/**
 * Created by Ad1 on 28.07.2016.
 */
public interface Constants {
    String SJ_COINS_PREFERENCES = "SJ_COINS_PREFERENCES";
    String ACCESS_TOKEN = "ACCESS_TOKEN";
    String REFRESH_TOKEN = "REFRESH_TOKEN";

    String BASE_URL = "https://192.168.102.251";

    String URL_AUTH_SERVICE = BASE_URL + ":8111/";
    String URL_VENDING_SERVICE = BASE_URL + ":8222/";
    String URL_COIN_SERVICE = BASE_URL + ":8080/";

    String GRANT_TYPE = "password";

    /**
     * Headers
     */
    String OAUTH = "OAUTH";
    String VENDING = "VENDING";
    String COINS = "COINS";
}

