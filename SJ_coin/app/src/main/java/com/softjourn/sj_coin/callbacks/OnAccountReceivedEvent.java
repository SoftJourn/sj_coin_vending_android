package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.accountInfo.Account;


public class OnAccountReceivedEvent {
    private Account mAccount;

    public OnAccountReceivedEvent(Account account) {
        this.mAccount = account;
    }

    public Account getAccount(){
        return mAccount;
    }
}
