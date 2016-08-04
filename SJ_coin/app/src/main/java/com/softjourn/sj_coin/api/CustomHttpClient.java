package com.softjourn.sj_coin.api;

import android.content.Context;
import android.util.Log;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Preferences;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ad1 on 02.08.2016.
 */
public class CustomHttpClient implements Constants{

    protected SSLSocketFactory trustCert(Context context, int rawRes){
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

    protected OkHttpClient okhttpOAuth() {
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
                .sslSocketFactory(trustCert(App.getContext(), R.raw.sj_coins_cert))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();
    }

    protected OkHttpClient okhttpVending() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request orRequest = request.newBuilder()
                                .addHeader("Authorization", "Bearer "+ Preferences.retrieveStringObject(ACCESS_TOKEN))
                                .build();
                        return chain.proceed(orRequest);
                    }
                })
                .sslSocketFactory(trustCert(App.getContext(), R.raw.vending))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();
    }
}
