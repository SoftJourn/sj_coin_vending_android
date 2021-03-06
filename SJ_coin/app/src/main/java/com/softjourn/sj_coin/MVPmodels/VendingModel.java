package com.softjourn.sj_coin.mvpmodels;


import android.content.Context;
import android.content.Intent;

import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.api.models.History;
import com.softjourn.sj_coin.api.models.machines.Machines;
import com.softjourn.sj_coin.api.models.products.Categories;
import com.softjourn.sj_coin.api.models.products.Favorites;
import com.softjourn.sj_coin.api.models.products.Featured;
import com.softjourn.sj_coin.api.models.products.Product;
import com.softjourn.sj_coin.api.vending.VendingApiProvider;
import com.softjourn.sj_coin.base.BaseModel;
import com.softjourn.sj_coin.datastorage.FavoritesStorage;
import com.softjourn.sj_coin.datastorage.FeaturesStorage;
import com.softjourn.sj_coin.events.OnAddedToFavorites;
import com.softjourn.sj_coin.events.OnFavoritesListReceived;
import com.softjourn.sj_coin.events.OnFeaturedProductsListReceived;
import com.softjourn.sj_coin.events.OnHistoryReceived;
import com.softjourn.sj_coin.events.OnMachinesListReceived;
import com.softjourn.sj_coin.events.OnRemovedFromFavorites;
import com.softjourn.sj_coin.events.OnServerErrorEvent;
import com.softjourn.sj_coin.managers.DataManager;
import com.softjourn.sj_coin.services.PurchaseService;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Extras;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VendingModel extends BaseModel implements Const,Extras {

    private final VendingApiProvider mApiProvider;

    private final DataManager mDataManager = new DataManager();

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
                mEventBus.post(new OnServerErrorEvent(errorMsg));
            }
        });
    }

    public void callFeaturedProductsList(String selectedMachine) {

        mApiProvider.getFeaturedProductsList(selectedMachine, new com.softjourn.sj_coin.api.callbacks.Callback<Featured>() {
            @Override
            public void onSuccess(Featured response) {
                FeaturesStorage.getInstance().setData(response);

                mEventBus.post(new OnFeaturedProductsListReceived());
            }

            @Override
            public void onError(String errorMsg) {
                mEventBus.post(new OnServerErrorEvent(errorMsg));
            }
        });
    }

    public void buyProductByID(String machineID, String id, Context context) {

        Intent intent = new Intent(Intent.ACTION_SYNC, null, context, PurchaseService.class);

        /* Send optional extras to Purchase IntentService */
        intent.putExtra("requestId", 101);
        intent.putExtra(EXTRAS_MACHINE_ID, machineID);
        intent.putExtra(EXTRAS_PRODUCT_ID, id);

        context.startService(intent);
    }

    public void callListFavorites() {

        mApiProvider.getListFavorites(new com.softjourn.sj_coin.api.callbacks.Callback<List<Favorites>>() {
            @Override
            public void onSuccess(List<Favorites> response) {
                FavoritesStorage.getInstance().setData(response);
                mEventBus.post(new OnFavoritesListReceived(response));
            }

            @Override
            public void onError(String errorMsg) {
                mEventBus.post(new OnServerErrorEvent(errorMsg));
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
                mEventBus.post(new OnServerErrorEvent(errorMsg));
            }
        });
    }

    public void removeProductFromFavorites(final String id) {

        mApiProvider.removeFromFavorites(id, new com.softjourn.sj_coin.api.callbacks.Callback<Void>() {
            @Override
            public void onSuccess(Void response) {
                mEventBus.post(new OnRemovedFromFavorites(Integer.parseInt(id)));
            }

            @Override
            public void onError(String errorMsg) {
                mEventBus.post(new OnServerErrorEvent(errorMsg));
            }
        });
    }

    public void getPurchaseHistory() {
        mApiProvider.getPurchaseHistory(new com.softjourn.sj_coin.api.callbacks.Callback<List<History>>() {
            @Override
            public void onSuccess(List<History> response) {
                mEventBus.post(new OnHistoryReceived(response));
            }

            @Override
            public void onError(String errorMsg) {
                mEventBus.post(new OnServerErrorEvent(errorMsg));

            }
        });
    }

    public List<Product> loadLocalProductList() {
        return mDataManager.loadLocalProductList();
    }

    public List<Product> loadBestSellers() {
        return mDataManager.loadBestSellers();
    }

    public List<Product> loadLastAdded() {
        return mDataManager.loadLastAdded();
    }

    public List<Product> loadFavorites() {
        return mDataManager.loadFavorites();
    }

    public List<Product> loadProductsFromDB(String category) {
        return mDataManager.loadProductsFromDB(category);
    }

    public List<Categories> loadCategories() {
        return mDataManager.loadCategories();
    }

    public void addToFavoriteLocal(Integer id) {
        mDataManager.addToFavorites(id);
    }

    public void removeFromFavoriteLocal(Integer id) {
        mDataManager.removeFromFavorites(id);
    }

    public List<Product> sortByName(String productsCategory, boolean isSortingForward) {
        List<Product> product = mDataManager.getConcreteCategory(productsCategory);

        if (isSortingForward) {
            Collections.sort(product, new Comparator<Product>() {
                @Override
                public int compare(Product lhs, Product rhs) {
                    return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
                }
            });
            return product;
        } else {
            Collections.sort(product, new Comparator<Product>() {
                @Override
                public int compare(Product lhs, Product rhs) {
                    return rhs.getName().toLowerCase().compareTo(lhs.getName().toLowerCase());
                }
            });
            return product;
        }
    }

    public List<Product> sortByPrice(String productsCategory, boolean isSortingForward) {
        List<Product> product = mDataManager.getConcreteCategory(productsCategory);

        if (isSortingForward) {
            Collections.sort(product, new Comparator<Product>() {
                @Override
                public int compare(Product lhs, Product rhs) {
                    return lhs.getPrice() - rhs.getPrice();
                }
            });
            return product;
        } else {
            Collections.sort(product, new Comparator<Product>() {
                @Override
                public int compare(Product lhs, Product rhs) {
                    return rhs.getPrice() - lhs.getPrice();
                }
            });
            return product;
        }
    }

    public boolean isSingleProductPresent(Integer id) {
        return mDataManager.isSingleProductPresent(id);
    }
}
