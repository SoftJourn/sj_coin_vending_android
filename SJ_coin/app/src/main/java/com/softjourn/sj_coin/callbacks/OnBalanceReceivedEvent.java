package com.softjourn.sj_coin.callbacks;


public class OnBalanceReceivedEvent {

    private String mBalance;

    public OnBalanceReceivedEvent(String balance) {
        this.mBalance = balance;
    }

    public String getBalance(){
        return mBalance;
    }
}
