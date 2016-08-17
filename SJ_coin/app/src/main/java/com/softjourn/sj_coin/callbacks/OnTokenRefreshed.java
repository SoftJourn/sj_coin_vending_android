package com.softjourn.sj_coin.callbacks;

/**
 * Created by Ad1 on 15.08.2016.
 */
public class OnTokenRefreshed {
    private boolean mIsSuccess;

    public OnTokenRefreshed(boolean isSuccess) {
        this.mIsSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }
}
