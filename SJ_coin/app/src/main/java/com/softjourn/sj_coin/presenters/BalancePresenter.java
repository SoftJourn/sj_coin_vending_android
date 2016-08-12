package com.softjourn.sj_coin.presenters;

import android.util.Log;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.callbacks.OnBalanceReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnLogin;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.model.Balance;
import com.softjourn.sj_coin.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalancePresenter extends BasePresenter implements Constants, IBalancePresenter {


    @Override
    public void callBalance() {
        createApiManager(COINS, URL_COIN_SERVICE);

        Callback<Balance> callback = new Callback<Balance>() {
            @Override
            public void onResponse(Call<Balance> call, Response<Balance> response) {
                if (!response.isSuccessful()) {
                    Log.d("Tag", "" + response.code());
                    Log.d("Tag", "" + response.message());
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                    mEventBus.post(new OnLogin(CALL_FAILED));
                } else {
                    Log.d("Tag", response.body().toString());
                    Balance balance = response.body();
                    mEventBus.post(new OnBalanceReceivedEvent(balance));
                }
            }

            @Override
            public void onFailure(Call<Balance> call, Throwable t) {
                mEventBus.post(new OnLogin(CALL_FAILED));
            }
        };
        mApiProvider.getBalance(callback);
    }
}

