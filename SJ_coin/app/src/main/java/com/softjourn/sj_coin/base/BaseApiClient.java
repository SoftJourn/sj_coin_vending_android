package com.softjourn.sj_coin.base;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softjourn.sj_coin.api.ApiProvider;
import com.softjourn.sj_coin.api.ApiService;
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

public abstract class BaseApiClient implements ApiProvider, Constants {

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    protected ApiService mApiService;

    public BaseApiClient(String URL) {
        OkHttpClient okHttpClient = createOkHttpClient();

        this.mApiService = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
                .create(ApiService.class);
    }

    public OkHttpClient createOkHttpClient() {
        return new OkHttpClient();
    }

    @Override
    public void makeLoginRequest(String email, String password, String type, Callback<Session> callback) {

    }

    @Override
    public void makeRefreshToken(String refreshToken, String type, Callback<Session> callback) {

    }

    @Override
    public void getMachines(Callback<List<Machines>> callback) {

    }

    @Override
    public void getConcreteMachine(String machineID, Callback<Machines> callback) {

    }

    @Override
    public void getProductsList(String selectedMachine, Callback<List<Product>> callback) {

    }

    @Override
    public void getBalance(Callback<Balance> callback) {

    }
}
