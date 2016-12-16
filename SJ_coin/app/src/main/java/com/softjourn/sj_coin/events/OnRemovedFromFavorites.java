package com.softjourn.sj_coin.events;

public class OnRemovedFromFavorites {

    private final int mId;

    public OnRemovedFromFavorites(int id){
        this.mId = id;
    }

    public int getId(){
        return mId;
    }
}
