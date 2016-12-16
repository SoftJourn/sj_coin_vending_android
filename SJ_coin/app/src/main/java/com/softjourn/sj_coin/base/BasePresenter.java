package com.softjourn.sj_coin.base;

public interface BasePresenter{

    void onCreate();

    void onDestroy();

    void logOut(String refreshToken);
}
