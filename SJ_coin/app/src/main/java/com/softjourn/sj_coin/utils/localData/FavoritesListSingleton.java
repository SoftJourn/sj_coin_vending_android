package com.softjourn.sj_coin.utils.localData;

import com.softjourn.sj_coin.model.products.Favorites;
import com.softjourn.sj_coin.utils.Const;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoritesListSingleton implements Const {

    private static FavoritesListSingleton sOurInstance;

    private List<Favorites> mFavoritesList;

    public static FavoritesListSingleton getInstance() {
        if (sOurInstance == null) {
            sOurInstance = new FavoritesListSingleton();
        }
        return sOurInstance;
    }

    public void setData(List<Favorites> products) {
        this.mFavoritesList = products;
    }

    public List<Favorites> getData() {
        return mFavoritesList == null ? Collections.<Favorites>emptyList() : mFavoritesList;
    }

    public void LocalAddToFavorites(int id) {
        if (id == INVALID_ID) {
            return;
        }

        if (mFavoritesList != null) {
            for (Favorites favorites : mFavoritesList) {
                if (favorites.getId() == id) {
                    return;
                }
            }
        } else {
            mFavoritesList = new ArrayList<>();
        }

        Favorites favorites = new Favorites();
        favorites.setId(id);
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

    public void onDestroy() {
        mFavoritesList.clear();
    }
}

