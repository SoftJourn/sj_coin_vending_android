package com.softjourn.sj_coin.api;

import com.softjourn.sj_coin.model.Session;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.Product;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by Ad1 on 02.08.2016.
 */
public interface ApiProvider {
    void makeLoginRequest(String email, String password, String type, Callback<Session> callback);

    void getMachines(Callback<List<Machines>> callback);

    void getConcreteMachine(String machineID, Callback<Machines> callback);

    void getProductsList(String selectedMachine, Callback<List<Product>> callback);
}
