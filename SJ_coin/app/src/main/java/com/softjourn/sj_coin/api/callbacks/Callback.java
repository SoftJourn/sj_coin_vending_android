package com.softjourn.sj_coin.api.callbacks;


public interface Callback<T>  {

    void onSuccess(T response);

    void onError(String errorMsg);
}
