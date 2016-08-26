package com.softjourn.sj_coin.MVPmodels;


import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.api.coins.CoinsApiProvider;
import com.softjourn.sj_coin.base.BaseModel;
import com.softjourn.sj_coin.callbacks.OnAccountReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.contratcts.ProfileContract;
import com.softjourn.sj_coin.model.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileModel extends BaseModel implements ProfileContract.Model{

    private CoinsApiProvider mApiProvider;

    @Override
    public void makeAccountCall() {
        createApiManager();

        Callback<Account> callback = new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (!response.isSuccessful()) {
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                } else {
                    Account account = response.body();
                    mEventBus.post(new OnAccountReceivedEvent(account));
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
            }
        };
        mApiProvider.getBalance(callback);
    }

    public void createApiManager() {
        mApiProvider = ApiManager.getInstance().getCoinsApiProvider();
    }
}
