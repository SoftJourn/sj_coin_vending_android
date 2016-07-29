package com.softjourn.sj_coin.api;


import android.content.Context;
import android.util.Log;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ad1 on 29.07.2016.
 */
public class ApiClient implements Constants{

    private static Retrofit retrofit = null;

    public static Retrofit getOAuthClient() {

        OkHttpClient okhttp = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {Request request = chain.request();
                        Request orRequest = request.newBuilder()
                                .addHeader("Authorization", "Basic dXNlcl9jcmVkOnN1cGVyc2VjcmV0")
                                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                .build();
                        return chain.proceed(orRequest);
                    }
                })
                .sslSocketFactory(trustCert(App.getContext(),R.raw.sj_coins_cert))
                .build();


        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_AUTH_SERVICE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttp)
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getVendingClient() {

        OkHttpClient okhttp = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {Request request = chain.request();
                        Request orRequest = request.newBuilder()
                                .addHeader("access_token", "Basic dXNlcl9jcmVkOnN1cGVyc2VjcmV0")
                                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                .build();
                        return chain.proceed(orRequest);
                    }
                })
                .sslSocketFactory(trustCert(App.getContext(), R.raw.vending))
                .build();


        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_VENDING_SERVICE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttp)
                    .build();
        }
        return retrofit;
    }

    private static SSLSocketFactory trustCert(Context context, int rawRes){
        try {
            KeyStore ksTrust = KeyStore.getInstance("BKS");
            InputStream inStream = context.getResources().openRawResource(rawRes);
            ksTrust.load(inStream, "changeit".toCharArray());
            // TrustManager decides which certificate authorities to use.
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ksTrust);
            TrustManager[] trustManagers = tmf.getTrustManagers();
            final X509TrustManager origTrustManager = (X509TrustManager)trustManagers[0];

            TrustManager[] wrappedTrustManagers = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return origTrustManager.getAcceptedIssuers();
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                            try {
                                origTrustManager.checkClientTrusted(certs, authType);
                            } catch (CertificateException e) {
                                Log.d("Tag", e.toString());
                            }
                        }


                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                            try {
                                origTrustManager.checkServerTrusted(certs, authType);
                            } catch (CertificateException e) {
                                Log.d("Tag", e.toString());
                            }
                        }
                    }
            };
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, wrappedTrustManagers, null);
            return sslContext.getSocketFactory();
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | KeyManagementException e) {
            Log.d("Tag",e.toString());
        }
        return null;
    }

    /*private enum Cert {
            AUTH(R.raw.sj_coins_cert, "changeit"),
            VENDING(R.raw.vending, "changeit"),
            COIN(R.raw.coin, "changeit");

            private int certResource;

            private Cert(int certResource, String password) {
                this.certResource = certResource;
            }
    }*/
}
