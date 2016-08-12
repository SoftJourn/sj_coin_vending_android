package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.Balance;

/**
 * Created by Ad1 on 10.08.2016.
 */
public class OnBalanceReceivedEvent {
    private Balance mBalance;

    public OnBalanceReceivedEvent (Balance balance) {
        this.mBalance = balance;
    }

    public Balance getBalance(){
        return mBalance;
    }
}
