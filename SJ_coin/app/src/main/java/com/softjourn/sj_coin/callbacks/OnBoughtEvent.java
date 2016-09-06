package com.softjourn.sj_coin.callbacks;

public class OnBoughtEvent {
    private boolean mIsSuccess;

    public OnBoughtEvent(boolean isSuccess){
        this.mIsSuccess = isSuccess;
    }

    public boolean isSuccess(){
        return mIsSuccess;
    }
}
