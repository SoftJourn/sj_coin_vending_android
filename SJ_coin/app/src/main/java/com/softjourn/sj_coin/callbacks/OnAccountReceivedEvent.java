package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.accountInfo.Account;

/**
 * Created by Ad1 on 10.08.2016.
 */
public class OnAccountReceivedEvent {
    private Account mAccount;

    public OnAccountReceivedEvent(Account account) {
        this.mAccount = account;
    }

    public Account getAccount(){
        return mAccount;
    }
}
