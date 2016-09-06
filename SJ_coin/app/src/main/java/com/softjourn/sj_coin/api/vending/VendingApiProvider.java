package com.softjourn.sj_coin.api.vending;

import com.softjourn.sj_coin.model.TransactionResponse;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.model.products.Products;

import java.util.List;

import retrofit2.Callback;

public interface VendingApiProvider {

    void getMachines(Callback<List<Machines>> callback);

    void getConcreteMachine(String machineID, Callback<Machines> callback);

    void getFeaturedProductsList(String selectedMachine, Callback<Products> callback);

    void getProductsList(String selectedMachine, Callback<List<Product>> callback);

    void buyProductByID(String id, Callback<TransactionResponse> callback);

    void addProductToFavorites(String id, Callback<TransactionResponse> callback);
}
