package com.softjourn.sj_coin.events;

public class OnLoginCallEvent {

    private final boolean mIsSuccess;

    public OnLoginCallEvent(boolean isSuccess){
        this.mIsSuccess = isSuccess;
    }

    public boolean isSuccess(){
        return mIsSuccess;
    }
}
