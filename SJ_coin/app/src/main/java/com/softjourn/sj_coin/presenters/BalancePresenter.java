package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.callbacks.OnBalanceReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnCallEvent;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.model.Balance;
import com.softjourn.sj_coin.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalancePresenter extends BasePresenter implements Constants, IBalancePresenter {


    @Override
    public void callBalance() {
        createApiManager();

        Callback<Balance> callback = new Callback<Balance>() {
            @Override
            public void onResponse(Call<Balance> call, Response<Balance> response) {
                if (!response.isSuccessful()) {
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                    mEventBus.post(new OnCallEvent(CALL_FAILED));
                } else {
                    Balance balance = response.body();
                    mEventBus.post(new OnBalanceReceivedEvent(balance));
                }
            }

            @Override
            public void onFailure(Call<Balance> call, Throwable t) {
                mEventBus.post(new OnCallEvent(CALL_FAILED));
            }
        };
        mApiProvider.getBalance(callback);
    }

    @Override
    public void createApiManager() {
        mApiProvider = ApiManager.getInstance().getCoinsApiProvider();
    }
}

