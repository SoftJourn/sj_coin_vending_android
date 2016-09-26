package com.softjourn.sj_coin.api.callbacks;

/**
 * Created by Andriy Ksenych on 14.09.2016.
 */
public interface Callback<T>  {

    void onSuccess(T response);

    void onError(String errorMsg);
}
