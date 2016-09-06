package com.softjourn.sj_coin.callbacks;

public class OnTokenRefreshed {
    private boolean mIsSuccess;

    public OnTokenRefreshed(boolean isSuccess) {
        this.mIsSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }
}