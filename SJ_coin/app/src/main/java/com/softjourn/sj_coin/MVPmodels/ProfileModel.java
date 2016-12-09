package com.softjourn.sj_coin.mvpmodels;

import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.api.coins.CoinsApiProvider;
import com.softjourn.sj_coin.api.models.accountInfo.Account;
import com.softjourn.sj_coin.api.models.accountInfo.Balance;
import com.softjourn.sj_coin.api.models.accountInfo.Cash;
import com.softjourn.sj_coin.api.models.accountInfo.DepositeTransaction;
import com.softjourn.sj_coin.base.BaseModel;
import com.softjourn.sj_coin.events.OnAccountReceivedEvent;
import com.softjourn.sj_coin.events.OnBalanceReceivedEvent;
import com.softjourn.sj_coin.events.OnBalanceUpdatedEvent;
import com.softjourn.sj_coin.events.OnServerErrorEvent;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Preferences;

public class ProfileModel extends BaseModel {

    private final CoinsApiProvider mCoinsApiProvider;

    public ProfileModel() {
        mCoinsApiProvider = ApiManager.getInstance().getCoinsApiProvider();
    }

    public void makeAccountCall() {

        mCoinsApiProvider.getBalance(new com.softjourn.sj_coin.api.callbacks.Callback<Account>() {
            @Override
            public void onSuccess(Account response) {
                Preferences.storeObject(Const.USER_BALANCE_PREFERENCES_KEY, response.getAmount());
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

    public void putMoneyToWallet(Cash scannedCode) {
        mCoinsApiProvider.putMoneyInWallet(scannedCode, new com.softjourn.sj_coin.api.callbacks.Callback<DepositeTransaction>() {
            @Override
            public void onSuccess(DepositeTransaction response) {
                mEventBus.post(new OnBalanceUpdatedEvent(String.valueOf(response.getRemain())));
            }

            @Override
            public void onError(String errorMsg) {
                mEventBus.post(new OnServerErrorEvent(errorMsg));
            }
        });
    }

    public void cancelRequest() {
        mCoinsApiProvider.cancelRequest();
    }
}
