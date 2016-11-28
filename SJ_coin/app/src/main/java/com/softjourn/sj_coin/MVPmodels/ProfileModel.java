package com.softjourn.sj_coin.MVPmodels;

import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.api.coins.CoinsApiProvider;
import com.softjourn.sj_coin.api_models.accountInfo.Account;
import com.softjourn.sj_coin.api_models.accountInfo.Balance;
import com.softjourn.sj_coin.base.BaseModel;
import com.softjourn.sj_coin.callbacks.OnAccountReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnBalanceReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;

public class ProfileModel extends BaseModel {

    private final CoinsApiProvider mCoinsApiProvider;

    public ProfileModel() {
        mCoinsApiProvider = ApiManager.getInstance().getCoinsApiProvider();
    }

    public void makeAccountCall() {

        mCoinsApiProvider.getBalance(new com.softjourn.sj_coin.api.callbacks.Callback<Account>() {
            @Override
            public void onSuccess(Account response) {
                mEventBus.post(new OnAccountReceivedEvent(response));
            }

            @Override
            public void onError(String errorMsg) {
                mEventBus.post(new OnServerErrorEvent(errorMsg));
            }
        });
    }

    public void makeBalanceCall() {
        mCoinsApiProvider.getAmount(new com.softjourn.sj_coin.api.callbacks.Callback<Balance>() {
            @Override
            public void onSuccess(Balance response) {
                mEventBus.post(new OnBalanceReceivedEvent(response.getAmount()));
            }

            @Override
            public void onError(String errorMsg) {
                mEventBus.post(new OnServerErrorEvent(errorMsg));
            }
        });
    }
}
