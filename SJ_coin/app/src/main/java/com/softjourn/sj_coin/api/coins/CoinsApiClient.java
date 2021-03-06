package com.softjourn.sj_coin.api.coins;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.api.CustomHttpClient;
import com.softjourn.sj_coin.api.TokenAuthenticator;
import com.softjourn.sj_coin.api.callbacks.Callback;
import com.softjourn.sj_coin.api.models.accountInfo.Account;
import com.softjourn.sj_coin.api.models.accountInfo.Balance;
import com.softjourn.sj_coin.base.BaseApiClient;
import com.softjourn.sj_coin.utils.Preferences;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;


public class CoinsApiClient extends BaseApiClient implements CoinsApiProvider {

    public CoinsApiClient() {
        super(URL_COIN_SERVICE);
    }

    private Call mCall;

    @Override
    public OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request orRequest = request.newBuilder()
                                .addHeader(HEADER_AUTHORIZATION_KEY, "Bearer " + Preferences.retrieveStringObject(ACCESS_TOKEN))
                                .build();
                        return chain.proceed(orRequest);
                    }
                })
                .addInterceptor(new TokenAuthenticator())
                .addInterceptor(getCacheInterceptor())
                .cache(getCacheForOkHttpClient())
                .sslSocketFactory(CustomHttpClient.trustCert(App.getContext()))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();
    }

    private void sendCallBack(com.softjourn.sj_coin.api.callbacks.Callback callback, retrofit2.Response response) {
        if (response.isSuccessful()) {
            callback.onSuccess(response.body());
        } else {
            callback.onError(response.message());
        }
    }

    @Override
    public void getBalance(final Callback<Account> callback) {
        mCall = mApiService.getBalance();
        mCall.enqueue(new retrofit2.Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, retrofit2.Response<Account> response) {
                sendCallBack(callback, response);
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                if (!call.isCanceled()) {
                    callback.onError(t.getMessage());
                }
            }
        });
    }

    @Override
    public void getAmount(final Callback<Balance> callback) {
        mCall = mApiService.getAmount();
        mCall.enqueue(new retrofit2.Callback<Balance>() {
            @Override
            public void onResponse(Call<Balance> call, retrofit2.Response<Balance> response) {
                sendCallBack(callback, response);
            }

            @Override
            public void onFailure(Call<Balance> call, Throwable t) {
                if (!call.isCanceled()) {
                    callback.onError(t.getMessage());
                }
            }
        });
    }

    public void cancelRequest() {
        mCall.cancel();
    }

}
