package com.softjourn.sj_coin.api.vending;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.api.CustomHttpClient;
import com.softjourn.sj_coin.base.BaseApiClient;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.utils.Preferences;

import java.io.IOException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Callback;

public class VendingProcessApiClient extends BaseApiClient implements VendingApiProvider {

    public VendingProcessApiClient() {
        super(URL_VENDING_SERVICE);
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
    public void getMachines(Callback<List<Machines>> callback) {
        mApiService.getMachines().enqueue(callback);
    }

    @Override
    public void getConcreteMachine(String machineID, Callback<Machines> callback) {
        mApiService.getConcreteMachine(machineID).enqueue(callback);
    }

    @Override
    public void getProductsList(String selectedMachine, Callback<List<Product>> callback) {
        mApiService.getProductsList(selectedMachine).enqueue(callback);
    }

}
