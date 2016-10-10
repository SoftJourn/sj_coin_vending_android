package com.softjourn.sj_coin.MVPmodels;


import android.app.Activity;

import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.api.vending.VendingApiProvider;
import com.softjourn.sj_coin.base.BaseModel;
import com.softjourn.sj_coin.callbacks.OnAddedToFavorites;
import com.softjourn.sj_coin.callbacks.OnAmountReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnBoughtEvent;
import com.softjourn.sj_coin.callbacks.OnFavoritesListReceived;
import com.softjourn.sj_coin.callbacks.OnFeaturedProductsListReceived;
import com.softjourn.sj_coin.callbacks.OnMachinesListReceived;
import com.softjourn.sj_coin.callbacks.OnRemovedFromFavorites;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.model.Amount;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.Categories;
import com.softjourn.sj_coin.model.products.Favorites;
import com.softjourn.sj_coin.model.products.Featured;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.realm.RealmController;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.RealmUtils;

import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

public class VendingModel extends BaseModel implements Const {

    private Realm mRealm = Realm.getDefaultInstance();

    private VendingApiProvider mApiProvider;

    public VendingModel() {
        mApiProvider = ApiManager.getInstance().getVendingProcessApiProvider();

    }

    public void callMachinesList() {
        mApiProvider.getMachines(new com.softjourn.sj_coin.api.callbacks.Callback<List<Machines>>() {
            @Override
            public void onSuccess(List<Machines> response) {
                mEventBus.post(new OnMachinesListReceived(response));
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    public void callFeaturedProductsList(String selectedMachine) {

        mApiProvider.getFeaturedProductsList(selectedMachine, new com.softjourn.sj_coin.api.callbacks.Callback<Featured>() {
            @Override
            public void onSuccess(Featured response) {
                RealmUtils.setRealmData(mRealm,response);
                mEventBus.post(new OnFeaturedProductsListReceived(response));
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    public void buyProductByID(String machineID, String id) {
        mApiProvider.buyProductByID(machineID, id, new com.softjourn.sj_coin.api.callbacks.Callback<Amount>() {
            @Override
            public void onSuccess(Amount response) {
                mEventBus.post(new OnAmountReceivedEvent(response));
                mEventBus.post(new OnBoughtEvent(response));
            }

            @Override
            public void onError(String errorMsg) {
                mEventBus.post(new OnServerErrorEvent(errorMsg));
            }
        });
    }

    public void getListFavorites() {

        mApiProvider.getListFavorites(new com.softjourn.sj_coin.api.callbacks.Callback<List<Favorites>>() {
            @Override
            public void onSuccess(List<Favorites> response) {
                RealmUtils.setRealmData(mRealm,response);
                mEventBus.post(new OnFavoritesListReceived(response));
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    public void addProductToFavorite(final int id) {

        mApiProvider.addProductToFavorites(id, new com.softjourn.sj_coin.api.callbacks.Callback<Void>() {
            @Override
            public void onSuccess(Void response) {
                    mEventBus.post(new OnAddedToFavorites(id));
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    public void removeProductFromFavorites(final String id) {

        mApiProvider.removeFromFavorites(id, new com.softjourn.sj_coin.api.callbacks.Callback<Void>() {
            @Override
            public void onSuccess(Void response) {
                    mEventBus.post(new OnRemovedFromFavorites(id));
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    public List<Product> loadLocalProductList(Activity activity) {
        return RealmController.with(activity).getProducts();
    }

    public List<Product> loadBestSellers(Activity activity) {
        return RealmController.with(activity).getProductsFromStaticCategory(Const.BEST_SELLERS);
    }

    public List<Product> loadLastAdded(Activity activity) {
        return RealmController.with(activity).getProductsFromStaticCategory(Const.LAST_ADDED);
    }

    public List<Product> loadFavorites(Activity activity) {
        return RealmController.with(activity).getProductsFromStaticCategory(Const.FAVORITES);
    }

    public List<Product> loadProductsFromDB(Activity activity, String category) {
        return RealmController.with(activity).getProductsFromCategory(category);
    }

    public List<Categories> loadCategories(Activity activity) {
        return RealmController.with(activity).getCategories();
    }

    public List<Product> sortByName(Activity activity, String productsCategory, boolean isSortingForward) {
        if (isSortingForward) {
            return RealmController.with(activity).getSortedProducts(productsCategory, "name", Sort.ASCENDING);
        } else {
            return RealmController.with(activity).getSortedProducts(productsCategory, "name", Sort.DESCENDING);
        }
    }

    public List<Product> sortByPrice(Activity activity, String productsCategory, boolean isSortingForward) {
        if (isSortingForward) {
            return RealmController.with(activity).getSortedProducts(productsCategory, "price", Sort.ASCENDING);
        } else {
            return RealmController.with(activity).getSortedProducts(productsCategory, "price", Sort.DESCENDING);
        }
    }


}
