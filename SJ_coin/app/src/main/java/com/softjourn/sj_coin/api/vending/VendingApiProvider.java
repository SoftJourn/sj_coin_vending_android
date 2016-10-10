package com.softjourn.sj_coin.api.vending;

import com.softjourn.sj_coin.api.callbacks.Callback;
import com.softjourn.sj_coin.model.Amount;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.Favorites;
import com.softjourn.sj_coin.model.products.Featured;
import com.softjourn.sj_coin.model.products.Product;

import java.util.List;

public interface VendingApiProvider {

    void getMachines(Callback<List<Machines>> callback);

    void getConcreteMachine(String machineID, Callback<Machines> callback);

    void getFeaturedProductsList(String selectedMachine, Callback<Featured> callback);

    void getProductsList(String selectedMachine, Callback<List<Product>> callback);

    void buyProductByID(String machinesID, String id, Callback<Amount> callback);

    void addProductToFavorites(int id, Callback<Void> callback);

    void getListFavorites(Callback<List<Favorites>> callback);

    void removeFromFavorites(String id, Callback<Void> callback);
}
