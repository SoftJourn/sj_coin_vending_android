package com.softjourn.sj_coin.callbacks;

public class OnLoginCallEvent {
    private boolean mIsSuccess;

    public OnLoginCallEvent(boolean isSuccess){
        this.mIsSuccess = isSuccess;
    }

    public boolean isSuccess(){
        return mIsSuccess;
    }
}
