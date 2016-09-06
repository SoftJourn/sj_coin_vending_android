package com.softjourn.sj_coin.api.coins;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.api.CustomHttpClient;
import com.softjourn.sj_coin.base.BaseApiClient;
import com.softjourn.sj_coin.model.accountInfo.Account;
import com.softjourn.sj_coin.model.accountInfo.Balance;
import com.softjourn.sj_coin.utils.Preferences;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Callback;


public class CoinsApiClient extends BaseApiClient implements CoinsApiProvider {

    public CoinsApiClient() {
        super(URL_COIN_SERVICE);
    }

    @Override
    public OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request orRequest = request.newBuilder()
                                .addHeader("Authorization", "Bearer " + Preferences.retrieveStringObject(ACCESS_TOKEN))
                                .build();
                        return chain.proceed(orRequest);
                    }
                })
                .sslSocketFactory(CustomHttpClient.trustCert(App.getContext()))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();
    }

    @Override
    public void getBalance(Callback<Account> callback) {
        mApiService.getBalance().enqueue(callback);
    }

    @Override
    public void getAmount(Callback<Balance> callback) {
        mApiService.getAmount().enqueue(callback);
    }


}
