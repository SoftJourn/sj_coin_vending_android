package com.softjourn.sj_coin.events;

public class OnTokenRevoked {

    private final boolean mIsSuccess;

    public OnTokenRevoked(boolean isSuccess) {
        this.mIsSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }
}
