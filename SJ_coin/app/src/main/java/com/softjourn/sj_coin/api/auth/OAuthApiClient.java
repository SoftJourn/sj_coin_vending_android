package com.softjourn.sj_coin.api.auth;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.api.CustomHttpClient;
import com.softjourn.sj_coin.api.models.Session;
import com.softjourn.sj_coin.base.BaseApiClient;
import com.softjourn.sj_coin.utils.Utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
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
                                .addHeader(HEADER_AUTHORIZATION_KEY, HEADER_AUTHORIZATION_VALUE)
                                .addHeader(HEADER_CONTENT_TYPE_KEY, HEADER_CONTENT_TYPE_VALUE)
                                .build();
                        return chain.proceed(orRequest);
                    }
                })
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

    @Override
    public void makeLoginRequest(String email, String password, String type, Callback<Session> callback) {
        mApiService.getAccessToken(email, password, type).enqueue(callback);
    }

    @Override
    public Session makeRefreshToken(String refreshToken, String type) {
        Session session = new Session();
        try {
            Call<Session> call = mApiService.getAccessTokenViaRefreshToken(refreshToken, type);
            session = call.execute().body();
            if (session != null) {
                Utils.storeSessionInfo(session);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return session;
    }

    @Override
    public void makeRevokeRefreshToken(String refreshToken, Callback<Void> callback) {
        mApiService.revokeRefreshToken(refreshToken).enqueue(callback);
    }
}
