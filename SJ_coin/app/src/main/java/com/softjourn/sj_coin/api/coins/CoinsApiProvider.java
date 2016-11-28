package com.softjourn.sj_coin.api.coins;


import com.softjourn.sj_coin.api.callbacks.Callback;
import com.softjourn.sj_coin.api_models.accountInfo.Account;
import com.softjourn.sj_coin.api_models.accountInfo.Balance;


public interface CoinsApiProvider {

    void getBalance(Callback<Account> callback);

    void getAmount(Callback<Balance> callback);
}
