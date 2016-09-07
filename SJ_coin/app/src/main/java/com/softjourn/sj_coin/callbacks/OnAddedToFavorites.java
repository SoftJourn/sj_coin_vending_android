package com.softjourn.sj_coin.callbacks;

public class OnAddedToFavorites {
    private String mId;

    public OnAddedToFavorites(String id){
        this.mId = id;
    }

    public String getId(){
        return mId;
    }
}
