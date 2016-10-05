package com.softjourn.sj_coin.base;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.softjourn.sj_coin.adapters.RealmIntegerListTypeAdapter;
import com.softjourn.sj_coin.adapters.RealmStringListTypeAdapter;
import com.softjourn.sj_coin.api.ApiService;
import com.softjourn.sj_coin.realm.realmTypes.RealmInteger;
import com.softjourn.sj_coin.realm.realmTypes.RealmString;
import com.softjourn.sj_coin.utils.Const;

import io.realm.RealmList;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseApiClient implements Const {

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .registerTypeAdapter(new TypeToken<RealmList<RealmString>>(){}.getType(),
                    RealmStringListTypeAdapter.INSTANCE)
            .registerTypeAdapter(new TypeToken<RealmList<RealmInteger>>(){}.getType(),
                    RealmIntegerListTypeAdapter.INSTANCE)
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
