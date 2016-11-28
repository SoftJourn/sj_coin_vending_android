package com.softjourn.sj_coin.callbacks;

public class OnBoughtEvent {

    private final boolean mIsSuccess;

    public OnBoughtEvent(boolean isSuccess) {
        this.mIsSuccess = isSuccess;
    }

    public boolean getAmount() {
        return mIsSuccess;
    }
}
