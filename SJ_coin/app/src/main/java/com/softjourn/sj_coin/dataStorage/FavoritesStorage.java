package com.softjourn.sj_coin.dataStorage;

import com.softjourn.sj_coin.model.products.Favorites;
import com.softjourn.sj_coin.model.products.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by omartynets on 04.11.2016.
 */
public class FavoritesStorage {

    private List<Favorites> mFavoritesList;

    private static FavoritesStorage ourInstance = new FavoritesStorage();

    public static FavoritesStorage getInstance() {
        return ourInstance;
    }

    private FavoritesStorage() {
    }

    public void setData(List<Favorites> products) {
        this.mFavoritesList = products;
    }

    public List<Favorites> getData() {
        return mFavoritesList == null ? Collections.<Favorites>emptyList() : mFavoritesList;
    }

    public void LocalAddToFavorites(Integer id) {
        List<Product> productList = ProductsStorage.getInstance().getData();

        if (mFavoritesList != null) {
            for (Favorites favorites : mFavoritesList) {
                if (favorites.getId().equals(id)) {
                    return;
                }
            }
        } else {
            mFavoritesList = new ArrayList<>();
        }

        Product product = new Product();
        for (int i=0;i<productList.size();i++){
            if (productList.get(i).getId().equals(id)){
                product = productList.get(i);
                break;
            }
        }
        Favorites favorites = new Favorites(product);
        mFavoritesList.add(favorites);
    }

    public void removeFromFavoritesLocal(Integer id) {
        for (int i = 0; i < mFavoritesList.size(); i++) {
            if (mFavoritesList.get(i).getId().equals(id)) {
                mFavoritesList.remove(i);
                break;
            }
        }
    }

    public void onDestroy() {
        if (mFavoritesList!=null) {
            mFavoritesList.clear();
        }
    }
}
