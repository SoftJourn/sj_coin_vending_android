package com.softjourn.sj_coin.realm;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.util.Log;

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

/**
 * Created by omartynets on 04.10.2016.
 */
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

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
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

    //find all objects in the Product class
    public RealmResults<Product> getProducts() {

        return realm.where(Product.class).findAll();
    }

    //query a single item with the given id
    public Product getProduct(int id) {

        return realm.where(Product.class).equalTo("id", id).findFirst();
    }

    //check if CustomizedProduct.class is empty
    public boolean hasProducts() {

        return !realm.where(Product.class).findAll().isEmpty();
    }

    //query all products from given category
    public RealmResults<Product> getProductsFromCategory(String category) {

        return realm.where(Product.class)
                .contains("category.name", category)
                .findAll();

    }

    public List<Integer> getLastAddedIDs() {
        List<Featured> lastAdded = realm.where(Featured.class).isNotEmpty("lastAdded").findAll();
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < lastAdded.size(); i++) {
            ids.add(lastAdded.get(i).getLastAdded().get(i).value);
        }
        return ids;
    }

    public RealmResults<Product> getLastAddedProducts() {
        RealmQuery<Product> query = realm.where(Product.class);
        List<Integer> ids = getLastAddedIDs();

        for (int id : ids) {
            query.equalTo("id",id);
        }
        return query.findAll();
    }

    public List<Integer> getBestSellersIDs() {
        List<Featured> lastAdded = realm.where(Featured.class).isNotEmpty("bestSellers").findAll();
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < lastAdded.size(); i++) {
            ids.add(lastAdded.get(i).getLastAdded().get(i).value);
        }
        return ids;
    }

    public RealmResults<Product> getBestSellersProducts() {
        RealmQuery<Product> query = realm.where(Product.class);

        List<Integer> ids = getBestSellersIDs();

        for (int id : ids) {
            query.equalTo("id",id);
        }
        return query.findAll();
    }

    public List<Integer> getFavoritesIDs() {
        List<Favorites> favorites = realm.where(Favorites.class).findAll();
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < favorites.size(); i++) {
            ids.add(favorites.get(i).getId());
        }
        return ids;
    }

    public RealmResults<Product> getFavoriteProducts() {
        List<Integer> ids = getFavoritesIDs();
        if(ids.size()<=0){
            return null;
        }
        RealmQuery<Product> query = realm.where(Product.class);

        for (int id : ids) {
            query.equalTo("id",id);
        }
        return query.findAll();
    }

    public RealmResults<Categories> getCategories() {
        Log.d("Tag",realm.where(Categories.class).findAll().toString());
        return realm.where(Categories.class).findAll();
    }



    public List<Product> getSortedProducts(String productsCategory, String sortingType, Sort sortOrder){
        switch (productsCategory){
            case Const.ALL_ITEMS:
                return getProducts().sort(sortingType, sortOrder);
            case Const.BEST_SELLERS:
                return getBestSellersProducts().sort(sortingType,sortOrder);
            case Const.LAST_ADDED:
                return getLastAddedProducts().sort(sortingType,sortOrder);
            default:
                return getProductsFromCategory(productsCategory).sort(sortingType, sortOrder);
        }
    }
}