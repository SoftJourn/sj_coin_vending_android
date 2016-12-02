package com.softjourn.sj_coin.events;

public class OnAddedToFavorites {
    private final int mId;

    public OnAddedToFavorites(int id){
        this.mId = id;
    }

    public int getId(){
        return mId;
    }
}
