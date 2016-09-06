package com.softjourn.sj_coin.callbacks;

public class OnCallEvent {
    private boolean mIsSuccess;

    public OnCallEvent(boolean isSuccess){
        this.mIsSuccess = isSuccess;
    }

    public boolean isSuccess(){
        return mIsSuccess;
    }
}
