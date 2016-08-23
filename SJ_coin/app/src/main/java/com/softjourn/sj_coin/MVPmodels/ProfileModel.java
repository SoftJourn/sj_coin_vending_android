package com.softjourn.sj_coin.MVPmodels;


import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.api.coins.CoinsApiProvider;
import com.softjourn.sj_coin.base.BaseModel;
import com.softjourn.sj_coin.callbacks.OnBalanceReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnCallEvent;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.contratcts.ProfileContract;
import com.softjourn.sj_coin.model.Balance;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileModel extends BaseModel implements ProfileContract.Model{

    private CoinsApiProvider mApiProvider;

    @Override
    public void makeBalanceCall() {
        createApiManager();

        Callback<Balance> callback = new Callback<Balance>() {
            @Override
            public void onResponse(Call<Balance> call, Response<Balance> response) {
                if (!response.isSuccessful()) {
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                    mEventBus.post(new OnCallEvent(Constants.CALL_FAILED));
                } else {
                    Balance balance = response.body();
                    mEventBus.post(new OnBalanceReceivedEvent(balance));
                }
            }

            @Override
            public void onFailure(Call<Balance> call, Throwable t) {
                mEventBus.post(new OnCallEvent(Constants.CALL_FAILED));
            }
        };
        mApiProvider.getBalance(callback);
    }

    @Override
    public String parseUserNameFromToken() {
        return Utils.getUserNameFromToken();
    }

    public void createApiManager() {
        mApiProvider = ApiManager.getInstance().getCoinsApiProvider();
    }
}
