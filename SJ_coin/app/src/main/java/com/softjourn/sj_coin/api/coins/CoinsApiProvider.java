package com.softjourn.sj_coin.api.coins;


import com.softjourn.sj_coin.model.Balance;

import retrofit2.Callback;

public interface CoinsApiProvider {

    void getBalance(Callback<Balance> callback);
}
