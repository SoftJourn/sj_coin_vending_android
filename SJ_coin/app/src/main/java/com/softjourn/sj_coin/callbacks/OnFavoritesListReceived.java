package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.api_models.products.Favorites;

import java.util.List;

public class OnFavoritesListReceived {

    private final List<Favorites> mList;

    public OnFavoritesListReceived(List<Favorites> favorites) {
        this.mList = favorites;
    }

    public List<Favorites> getFavorites() {
        return mList;
    }
}
