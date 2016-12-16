package com.softjourn.sj_coin.events;

/**
 * Created by omartynets on 08.12.2016.
 * CurrentEvent is using to redraw recyclerView after Favorite was removed
 * on ProductDetails from FavoriteCategory
 */

public class OnRemoveItemFromCategoryFavorite {

    private final int mId;

    public OnRemoveItemFromCategoryFavorite(int id) {
        this.mId = id;
    }

    public int getId() {
        return mId;
    }
}
