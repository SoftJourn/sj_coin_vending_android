package com.softjourn.sj_coin.events;

public class OnBalanceReceivedEvent {

    private final String mBalance;

    public OnBalanceReceivedEvent(String balance) {
        this.mBalance = balance;
    }

    public String getBalance(){
        return mBalance;
    }
}
