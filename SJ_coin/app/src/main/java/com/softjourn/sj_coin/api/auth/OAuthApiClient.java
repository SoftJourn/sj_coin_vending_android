package com.softjourn.sj_coin.api.auth;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.api.CustomHttpClient;
import com.softjourn.sj_coin.base.BaseApiClient;
import com.softjourn.sj_coin.model.Session;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Callback;

public class OAuthApiClient extends BaseApiClient implements OAuthApiProvider {

    public OAuthApiClient() {
        super(URL_AUTH_SERVICE);
    }

    @Override
    public OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request orRequest = request.newBuilder()
                                .addHeader("Authorization", "Basic dXNlcl9jcmVkOnN1cGVyc2VjcmV0")
                                .addHeader("Content-Type", "application/x-www-form-urlencoded")
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
    public void makeLoginRequest(String email, String password, String type, Callback<Session> callback) {
        mApiService.getAccessToken(email, password, type).enqueue(callback);
    }

    @Override
    public void makeRefreshToken(String refreshToken, String type, Callback<Session> callback) {
        mApiService.getAccessTokenViaRefreshToken(refreshToken, type).enqueue(callback);
    }
}
