package com.softjourn.sj_coin.events;

public class OnTokenRefreshed {

    private final boolean mIsSuccess;

    public OnTokenRefreshed(boolean isSuccess) {
        this.mIsSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }
}
