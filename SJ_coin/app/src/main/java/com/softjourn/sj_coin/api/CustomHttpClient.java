package com.softjourn.sj_coin.api;

import android.content.Context;
import android.util.Log;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;


public class CustomHttpClient implements Constants{

    public static SSLSocketFactory trustCert(Context context) {
        try {
            KeyStore ksTrust = KeyStore.getInstance("BKS");
            InputStream inStream = context.getResources().openRawResource(R.raw.coin);
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
}
