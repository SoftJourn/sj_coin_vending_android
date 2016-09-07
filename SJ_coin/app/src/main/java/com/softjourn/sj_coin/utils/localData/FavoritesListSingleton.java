package com.softjourn.sj_coin.utils.localData;

import com.softjourn.sj_coin.model.products.Favorites;

import java.util.List;

public class FavoritesListSingleton {

    private static FavoritesListSingleton sOurInstance = new FavoritesListSingleton();

    private List<Favorites> mFavoritesList;

    private FavoritesListSingleton() {
    }

    public static FavoritesListSingleton getInstance() {
        return sOurInstance;
    }

    public void setData(List<Favorites> products) {
        this.mFavoritesList = products;
    }

    public List<Favorites> getData() {
        return mFavoritesList;
    }

    public void LocalAddToFavorites(String id) {
        Favorites favorites = new Favorites();
        favorites.setId(Integer.parseInt(id));
        mFavoritesList.add(favorites);
    }

    public void LocalRemoveFromFavorites(String id) {
        for (int i = 0; i < mFavoritesList.size(); i++) {
            if (mFavoritesList.get(i).getId() == Integer.parseInt(id)) {
                mFavoritesList.remove(i);
                break;
            }
        }
    }

    public void onDestroy(){
        mFavoritesList.clear();
    }
}

