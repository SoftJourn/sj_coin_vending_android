package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.Amount;

public class OnBoughtEvent {

    private final Amount mIsSuccess;

    public OnBoughtEvent(Amount isSuccess){
        this.mIsSuccess = isSuccess;
    }

    public Amount getAmount(){
        return mIsSuccess;
    }
}
