package com.softjourn.sj_coin.callbacks;

public class OnBoughtEvent {

    private final String mIsSuccess;

    public OnBoughtEvent(String isSuccess){
        this.mIsSuccess = isSuccess;
    }

    public String getAmount(){
        return mIsSuccess;
    }
}
