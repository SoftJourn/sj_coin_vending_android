package com.softjourn.sj_coin.api;

import android.content.Context;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.utils.Const;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


public class CustomHttpClient implements Const {

    public static SSLSocketFactory trustCert(Context context) {
        try {
            KeyStore ksTrust = KeyStore.getInstance("BKS");
            InputStream inStream = context.getResources().openRawResource(R.raw.coin);
            ksTrust.load(inStream, "changeit".toCharArray());
            // TrustManager decides which certificate authorities to use.
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ksTrust);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            return sslContext.getSocketFactory();
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | KeyManagementException e) {
        }
        return null;
    }
}
