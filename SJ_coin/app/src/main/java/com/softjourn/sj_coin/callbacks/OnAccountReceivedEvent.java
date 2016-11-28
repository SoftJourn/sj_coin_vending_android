package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.api_models.accountInfo.Account;


public class OnAccountReceivedEvent {
    private final Account mAccount;

    public OnAccountReceivedEvent(Account account) {
        this.mAccount = account;
    }

    public Account getAccount(){
        return mAccount;
    }
}
