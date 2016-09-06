package com.softjourn.sj_coin.base;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softjourn.sj_coin.api.ApiService;
import com.softjourn.sj_coin.utils.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseApiClient implements Constants {

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
}