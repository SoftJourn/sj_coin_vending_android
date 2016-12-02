package com.softjourn.sj_coin.api.coins;


import com.softjourn.sj_coin.api.callbacks.Callback;
import com.softjourn.sj_coin.api.models.accountInfo.Account;
import com.softjourn.sj_coin.api.models.accountInfo.Balance;
import com.softjourn.sj_coin.api.models.accountInfo.Cash;
import com.softjourn.sj_coin.api.models.accountInfo.DepositeTransaction;


public interface CoinsApiProvider {

    void getBalance(Callback<Account> callback);

    void getAmount(Callback<Balance> callback);

    void putMoneyInWallet(Cash scannedCode, Callback<DepositeTransaction> callback);
}
