package com.softjourn.sj_coin.MVPmodels;


import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.api.coins.CoinsApiProvider;
import com.softjourn.sj_coin.base.BaseModel;
import com.softjourn.sj_coin.callbacks.OnAccountReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnBalanceReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.contratcts.ProfileContract;
import com.softjourn.sj_coin.model.History;
import com.softjourn.sj_coin.model.accountInfo.Account;
import com.softjourn.sj_coin.model.accountInfo.Balance;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void makeBalanceCall() {
        createApiManager();

        Callback<Balance> callback = new Callback<Balance>() {
            @Override
            public void onResponse(Call<Balance> call, Response<Balance> response) {
                if (!response.isSuccessful()) {
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                } else {
                    mEventBus.post(new OnBalanceReceivedEvent(response.body().getAmount()));
                }
            }

            @Override
            public void onFailure(Call<Balance> call, Throwable t) {
                t.printStackTrace();
            }
        };
        mApiProvider.getAmount(callback);
    }

    //This method is based on dummy data to show work on UI
    @Override
    public List<History> loadHistory() {

        History history = new History();
        history.setDate("01/02/2016");
        history.setPrice("7");
        history.setName("Fanta");

        History history1 = new History();
        history1.setDate("02/02/2016");
        history1.setPrice("3");
        history1.setName("Lays");

        History history2 = new History();
        history2.setDate("03/02/2016");
        history2.setPrice("5");
        history2.setName("Cola");

        List<History> tempList = new ArrayList<>();
        tempList.add(history);
        tempList.add(history1);
        tempList.add(history2);

        return tempList;
    }

    public void createApiManager() {
        mApiProvider = ApiManager.getInstance().getCoinsApiProvider();
    }
}
