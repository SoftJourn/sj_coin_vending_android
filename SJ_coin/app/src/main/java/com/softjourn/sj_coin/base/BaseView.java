package com.softjourn.sj_coin.base;


public interface BaseView {

    void showProgress(String message);

    void hideProgress();

    void showToastMessage();

    void showNoInternetError();
}
