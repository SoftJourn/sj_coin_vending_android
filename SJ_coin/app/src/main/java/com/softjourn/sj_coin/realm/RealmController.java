package com.softjourn.sj_coin.realm;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import com.softjourn.sj_coin.model.products.Featured;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.utils.Const;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
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

    public List<Product> getLastAddedProducts() {
        List<Integer> ids = getLastAddedIDs();
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            products.add(realm.where(Product.class).equalTo("id", ids.get(i)).findFirst());
        }
        return products;
    }

    public List<Integer> getBestSellersIDs() {
        List<Featured> lastAdded = realm.where(Featured.class).isNotEmpty("bestSellers").findAll();
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < lastAdded.size(); i++) {
            ids.add(lastAdded.get(i).getLastAdded().get(i).value);
        }
        return ids;
    }

    public List<Product> getBestSellersProducts() {
        List<Integer> ids = getBestSellersIDs();
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            products.add(realm.where(Product.class).equalTo("id", ids.get(i)).findFirst());
        }
        return products;
    }

    public List<Product> getSortedProducts(String productsCategory, String sortingType, Sort sortOrder){
        switch (productsCategory){
            case Const.ALL_ITEMS:
                return realm.where(Product.class).findAllSorted(sortingType, sortOrder);

            case Const.BEST_SELLERS:
                break;

            case Const.LAST_ADDED:
                break;
        }
        return null;
    }
}