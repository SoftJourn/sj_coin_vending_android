package com.softjourn.sj_coin.base;

public interface BasePresenter{

    void onCreate();

    void onDestroy();

    void refreshToken(String refreshToken);

    void logOut(String refreshToken);
}
