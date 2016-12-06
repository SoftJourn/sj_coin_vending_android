package com.softjourn.sj_coin.base;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.api.ApiService;
import com.softjourn.sj_coin.utils.Const;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseApiClient implements Const {

    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    protected final ApiService mApiService;

    protected BaseApiClient(String URL) {
        File httpCacheDirectory = new File(App.getContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient okHttpClient = createOkHttpClient();

        this.mApiService = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
                .create(ApiService.class);
    }

    protected OkHttpClient createOkHttpClient() {
        return new OkHttpClient();
    }

    protected Interceptor getCacheInterceptor() {
        return cacheControlInterceptor;
    }

    private static final Interceptor cacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder request = originalRequest.newBuilder();
            Response response = chain.proceed(request.build());
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", String.format("max-age=%d", 50000))
                    .build();
        }
    };

    protected Cache getCacheForOkHttpClient() {
        File httpCacheDirectory = new File(App.getContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(httpCacheDirectory, cacheSize);
    }
}
