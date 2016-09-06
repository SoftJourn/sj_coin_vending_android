package com.softjourn.sj_coin.callbacks;

public class OnAddedToFavorites {
    private boolean mIsSuccess;

    public OnAddedToFavorites(boolean isSuccess){
        this.mIsSuccess = isSuccess;
    }

    public boolean isSuccess(){
        return mIsSuccess;
    }
}
