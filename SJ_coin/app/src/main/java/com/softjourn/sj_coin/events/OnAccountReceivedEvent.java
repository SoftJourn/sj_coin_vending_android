package com.softjourn.sj_coin.events;

import com.softjourn.sj_coin.api.models.accountInfo.Account;


public class OnAccountReceivedEvent {
    private final Account mAccount;

    public OnAccountReceivedEvent(Account account) {
        this.mAccount = account;
    }

    public Account getAccount(){
        return mAccount;
    }
}
