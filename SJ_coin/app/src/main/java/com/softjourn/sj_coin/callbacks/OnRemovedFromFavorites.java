package com.softjourn.sj_coin.callbacks;

public class OnRemovedFromFavorites {

    private final String mId;

    public OnRemovedFromFavorites(String id){
        this.mId = id;
    }

    public String getId(){
        return mId;
    }
}
