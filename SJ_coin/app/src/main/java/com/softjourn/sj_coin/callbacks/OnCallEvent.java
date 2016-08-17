package com.softjourn.sj_coin.callbacks;

/**
 * Created by Ad1 on 02.08.2016.
 */
public class OnCallEvent {
    private boolean mIsSuccess;

    public OnCallEvent(boolean isSuccess){
        this.mIsSuccess = isSuccess;
    }

    public boolean isSuccess(){
        return mIsSuccess;
    }
}
