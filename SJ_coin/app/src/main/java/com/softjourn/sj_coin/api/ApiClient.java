package com.softjourn.sj_coin.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softjourn.sj_coin.model.Balance;
import com.softjourn.sj_coin.model.Session;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.utils.Constants;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ad1 on 29.07.2016.
 */
public class ApiClient implements ApiProvider, Constants {

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private ApiService mApiService;

    public ApiClient(String headers, String URL) {
        OkHttpClient okHttpClient;
        switch (headers) {
            case OAUTH:
                okHttpClient = new CustomHttpClient().okhttpOAuth();
                break;
            case VENDING:
                okHttpClient = new CustomHttpClient().okhttpVending();
                break;
            case COINS:
                okHttpClient = new CustomHttpClient().okhttpVending();
                break;
            default:
                okHttpClient = new CustomHttpClient().okhttpOAuth();
        }

        this.mApiService = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
                .create(ApiService.class);
    }

    @Override
    public void makeLoginRequest(String email, String password, String type, Callback<Session> callback) {
        mApiService.getAccessToken(email, password, type).enqueue(callback);
    }

    @Override
    public void getMachines(Callback<List<Machines>> callback) {
        mApiService.getMachines().enqueue(callback);
    }

    @Override
    public void getConcreteMachine(String machineID, Callback<Machines> callback) {
        mApiService.getConcreteMachine(machineID).enqueue(callback);
    }

    @Override
    public void getProductsList(String selectedMachine, Callback<List<Product>> callback) {
        mApiService.getProductsList(selectedMachine).enqueue(callback);
    }

    @Override
    public void getBalance(Callback<Balance> callback) {
        mApiService.getBalance().enqueue(callback);
    }
}

