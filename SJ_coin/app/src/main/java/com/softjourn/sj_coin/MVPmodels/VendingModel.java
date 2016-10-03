package com.softjourn.sj_coin.MVPmodels;


import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.api.vending.VendingApiProvider;
import com.softjourn.sj_coin.base.BaseModel;
import com.softjourn.sj_coin.callbacks.OnAddedToFavorites;
import com.softjourn.sj_coin.callbacks.OnAmountReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnBoughtEvent;
import com.softjourn.sj_coin.callbacks.OnFavoritesListReceived;
import com.softjourn.sj_coin.callbacks.OnFeaturedProductsListReceived;
import com.softjourn.sj_coin.callbacks.OnMachinesListReceived;
import com.softjourn.sj_coin.callbacks.OnProductsListReceived;
import com.softjourn.sj_coin.callbacks.OnRemovedFromFavorites;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.model.Amount;
import com.softjourn.sj_coin.model.CustomizedProduct;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.BestSeller;
import com.softjourn.sj_coin.model.products.Drink;
import com.softjourn.sj_coin.model.products.Favorites;
import com.softjourn.sj_coin.model.products.LastAdded;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.model.products.Products;
import com.softjourn.sj_coin.model.products.Snack;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Utils;
import com.softjourn.sj_coin.utils.localData.FavoritesListSingleton;
import com.softjourn.sj_coin.utils.localData.FeaturedProductsSingleton;
import com.softjourn.sj_coin.utils.localData.ProductsListSingleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VendingModel extends BaseModel implements Const {

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

    public void callConcreteMachine(String machineID) {

        mApiProvider.getConcreteMachine(machineID, new com.softjourn.sj_coin.api.callbacks.Callback<Machines>() {
            @Override
            public void onSuccess(Machines response) {
                Utils.storeConcreteMachineInfo(response);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    public void callFeaturedProductsList(String machineID) {

        mApiProvider.getFeaturedProductsList(machineID, new com.softjourn.sj_coin.api.callbacks.Callback<Products>() {
            @Override
            public void onSuccess(Products response) {
                FeaturedProductsSingleton.getInstance().setData(response);
                mEventBus.post(new OnFeaturedProductsListReceived(response));
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    public void callProductsList(String machineID) {

        mApiProvider.getProductsList(machineID, new com.softjourn.sj_coin.api.callbacks.Callback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> response) {
                mEventBus.post(new OnProductsListReceived(response));
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    public void buyProductByID(String id) {
        mApiProvider.buyProductByID(id, new com.softjourn.sj_coin.api.callbacks.Callback<Amount>() {
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
                FavoritesListSingleton.getInstance().setData(response);
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
                FavoritesListSingleton.getInstance().LocalAddToFavorites(id);
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
                FavoritesListSingleton.getInstance().LocalRemoveFromFavorites(id);
                mEventBus.post(new OnRemovedFromFavorites(id));
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    public List<Product> loadLocalProductList() {
        return ProductsListSingleton.getInstance().getData();
    }

    public Products loadLocalFeaturedProductList() {
        return FeaturedProductsSingleton.getInstance().getData();
    }

    public List<BestSeller> loadBestSellers() {
        return FeaturedProductsSingleton.getInstance().getData().getBestSellers();
    }

    public List<LastAdded> loadLastAdded() {
        return FeaturedProductsSingleton.getInstance().getData().getNewProducts();
    }

    public List<Drink> loadDrink() {
        return FeaturedProductsSingleton.getInstance().getData().getDrink();
    }

    public List<Snack> loadSnack() {
        return FeaturedProductsSingleton.getInstance().getData().getSnack();
    }

    public List<CustomizedProduct> loadFavorites() {

        List<CustomizedProduct> favoritesProducts = new ArrayList<>();
        favoritesProducts.addAll(getFavoriteProducts(FeaturedProductsSingleton.getInstance().getData().getDrink()));
        favoritesProducts.addAll(getFavoriteProducts(FeaturedProductsSingleton.getInstance().getData().getSnack()));
        return favoritesProducts;
    }

    private List<CustomizedProduct> getFavoriteProducts(List<? extends CustomizedProduct> products) {

        List<CustomizedProduct> favoritesProducts = new ArrayList<>();

        for (Favorites favorites : FavoritesListSingleton.getInstance().getData()) {

            for (CustomizedProduct product : products) {
                if (favorites.getId() == product.getId()) {
                    favoritesProducts.add(product);
                    break;
                }
            }
        }

        return favoritesProducts;
    }

    public List<? extends CustomizedProduct> sortByName(List<? extends CustomizedProduct> product, boolean isSortingForward) {
        if (isSortingForward) {
            Collections.sort(product, new Comparator<CustomizedProduct>() {
                @Override
                public int compare(CustomizedProduct lhs, CustomizedProduct rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            });
            return product;
        } else {
            Collections.sort(product, new Comparator<CustomizedProduct>() {
                @Override
                public int compare(CustomizedProduct lhs, CustomizedProduct rhs) {
                    return rhs.getName().compareTo(lhs.getName());
                }
            });
            return product;
        }
    }

    public List<? extends CustomizedProduct> sortByPrice(List<? extends CustomizedProduct> product, boolean isSortingForward) {
        if (isSortingForward) {
            Collections.sort(product, new Comparator<CustomizedProduct>() {
                @Override
                public int compare(CustomizedProduct lhs, CustomizedProduct rhs) {
                    return lhs.getPrice() - rhs.getPrice();
                }
            });
            return product;
        } else {
            Collections.sort(product, new Comparator<CustomizedProduct>() {
                @Override
                public int compare(CustomizedProduct lhs, CustomizedProduct rhs) {
                    return rhs.getPrice() - lhs.getPrice();
                }
            });
            return product;
        }
    }
}
