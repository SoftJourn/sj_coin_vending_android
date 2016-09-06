package com.softjourn.sj_coin.api.coins;


import com.softjourn.sj_coin.model.accountInfo.Account;
import com.softjourn.sj_coin.model.accountInfo.Balance;

import retrofit2.Callback;

public interface CoinsApiProvider {

    void getBalance(Callback<Account> callback);

    void getAmount(Callback<Balance> callback);
}