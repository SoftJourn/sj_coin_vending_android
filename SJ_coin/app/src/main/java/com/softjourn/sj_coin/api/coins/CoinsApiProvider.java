package com.softjourn.sj_coin.api.coins;


import com.softjourn.sj_coin.model.Account;

import retrofit2.Callback;

public interface CoinsApiProvider {

    void getBalance(Callback<Account> callback);
}
