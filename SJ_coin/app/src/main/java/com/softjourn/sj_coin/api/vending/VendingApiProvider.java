package com.softjourn.sj_coin.api.vending;

import com.softjourn.sj_coin.api.callbacks.Callback;
import com.softjourn.sj_coin.api.models.Amount;
import com.softjourn.sj_coin.api.models.History;
import com.softjourn.sj_coin.api.models.machines.Machines;
import com.softjourn.sj_coin.api.models.products.Favorites;
import com.softjourn.sj_coin.api.models.products.Featured;

import java.util.List;

public interface VendingApiProvider {

    void getMachines(Callback<List<Machines>> callback);

    void getFeaturedProductsList(String selectedMachine, Callback<Featured> callback);

    void buyProductByID(String machinesID, String id, Callback<Amount> callback);

    void addProductToFavorites(int id, Callback<Void> callback);

    void getListFavorites(Callback<List<Favorites>> callback);

    void removeFromFavorites(String id, Callback<Void> callback);

    void getPurchaseHistory(Callback<List<History>> callback);
}
