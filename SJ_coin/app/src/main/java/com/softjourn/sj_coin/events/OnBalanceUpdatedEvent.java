package com.softjourn.sj_coin.events;

/**
 * Created by omartynets on 02.12.2016.
 */

public class OnBalanceUpdatedEvent {

    private final String mBalance;

    public OnBalanceUpdatedEvent(String balance) {
        this.mBalance = balance;
    }

    public String getBalance() {
        return mBalance;
    }
}
