package com.softjourn.sj_coin.realm;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import com.softjourn.sj_coin.model.History;
import com.softjourn.sj_coin.model.products.Categories;
import com.softjourn.sj_coin.model.products.Favorites;
import com.softjourn.sj_coin.model.products.Featured;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.utils.Const;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //clear all objects from Database
    public void clearAll() {

        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public void addToFavoriteLocal(Integer id) {
        Favorites favorites = new Favorites();
        favorites.setId(id);

        realm.beginTransaction();
        realm.copyToRealm(favorites);
        realm.commitTransaction();
    }

    public void removeFromFavoritesLocal(Integer id) {
        RealmResults<Favorites> result = realm.where(Favorites.class).equalTo("id",id).findAll();
        realm.beginTransaction();
        result.deleteAllFromRealm();
        realm.commitTransaction();
    }

    //find all objects in the Product class
    public RealmResults<Product> getProducts() {

        return realm.where(Product.class).findAll();
    }

    //query all products from given category
    public RealmResults<Product> getProductsFromCategory(String category) {

        return realm.where(Product.class)
                .contains("category.name", category)
                .findAll();

    }

    //query to get products from Last Added or Best Sellers or Favorites.
    //First needed to get Ids for chosen category to make query from all products
    //with given list of IDs
    public RealmResults<Product> getProductsFromStaticCategory(String category) {

        CategorizedProductsIds catIds = new CategorizedProductsIds(category);
        List<Integer> ids = catIds.getIds();
        assert ids != null;
        if (ids.size() <= 0) {
            return null;
        }
        RealmQuery<Product> query = realm.where(Product.class);

        return query.in("id",ids.toArray(new Integer[ids.size()])).findAll();
    }

    //query all Categories (Drink, Snack etc.) from JSON
    //to build correct UI.
    public RealmResults<Categories> getCategories() {
        return realm.where(Categories.class).findAll();
    }


    //Sorting products after query
    public List<Product> getSortedProducts(String productsCategory, String sortingType, Sort sortOrder) {
        switch (productsCategory) {
            case Const.ALL_ITEMS:
                return getProducts().sort(sortingType, sortOrder);
            case Const.BEST_SELLERS:
                return getProductsFromStaticCategory(Const.BEST_SELLERS).sort(sortingType, sortOrder);
            case Const.LAST_ADDED:
                return getProductsFromStaticCategory(Const.LAST_ADDED).sort(sortingType, sortOrder);
            case Const.FAVORITES:
                return getProductsFromStaticCategory(Const.FAVORITES).sort(sortingType, sortOrder);
            default:
                return getProductsFromCategory(productsCategory).sort(sortingType, sortOrder);
        }
    }


    /**
     * Query all IDs from given static Category.
     */
    private class CategorizedProductsIds {

        final String mCategory;

        private CategorizedProductsIds(String category) {
            this.mCategory = category;
        }

        private List<Integer> getIds() {
            switch (mCategory) {
                case Const.LAST_ADDED:
                    return getLastAddedIDs();
                case Const.BEST_SELLERS:
                    return getBestSellersIDs();
                case Const.FAVORITES:
                    return getFavoritesIDs();
                case Const.PURCHASE:
                    return getPurchaseIDs();
            }
            return null;
        }

        private List<Integer> getPurchaseIDs() {
            List<History> purchase = realm.where(History.class).findAll();
            List<Integer> ids = new ArrayList<>();
            for (int i = 0; i < purchase.size(); i++) {
                try {
                    ids.add(purchase.get(i).getId());
                } catch (IndexOutOfBoundsException e) {
                    return new ArrayList<>();
                }
            }
            return ids;
        }

        private List<Integer> getLastAddedIDs() {
            List<Featured> lastAdded = realm.where(Featured.class).isNotEmpty("lastAdded").findAll();
            List<Integer> ids = new ArrayList<>();
            for (int i = 0; i < lastAdded.size(); i++) {
                try {
                    ids.add(lastAdded.get(i).getLastAdded().get(i).value);
                } catch (IndexOutOfBoundsException e) {
                    return new ArrayList<>();
                }
            }
            return ids;
        }

        private List<Integer> getBestSellersIDs() {
            List<Featured> bestSellers = realm.where(Featured.class).isNotEmpty("bestSellers").findAll();
            List<Integer> ids = new ArrayList<>();
            for (int i = 0; i < bestSellers.size(); i++) {
                try {
                    ids.add(bestSellers.get(i).getLastAdded().get(i).value);
                } catch (IndexOutOfBoundsException e) {
                    return new ArrayList<>();
                }
            }
            return ids;
        }

        private List<Integer> getFavoritesIDs() {
            List<Favorites> favorites = realm.where(Favorites.class).findAll();
            List<Integer> ids = new ArrayList<>();
            for (int i = 0; i < favorites.size(); i++) {
                try {
                    ids.add(favorites.get(i).getId());
                } catch (IndexOutOfBoundsException e) {
                    return new ArrayList<>();
                }
            }
            return ids;
        }
    }
}