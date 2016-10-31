package com.softjourn.sj_coin.api.vending;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.api.CustomHttpClient;
import com.softjourn.sj_coin.api.callbacks.Callback;
import com.softjourn.sj_coin.base.BaseApiClient;
import com.softjourn.sj_coin.model.Amount;
import com.softjourn.sj_coin.model.History;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.Favorites;
import com.softjourn.sj_coin.model.products.Featured;
import com.softjourn.sj_coin.utils.Preferences;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;

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
                                .addHeader(HEADER_AUTHORIZATION_KEY, "Bearer " + Preferences.retrieveStringObject(ACCESS_TOKEN))
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
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                .build();
    }

    private void sendCallBack(com.softjourn.sj_coin.api.callbacks.Callback callback, retrofit2.Response response) {
        if (response.isSuccessful()) {
            callback.onSuccess(response.body());
        } else {
            callback.onError(String.valueOf(response.code()));
        }
    }

    @Override
    public void getMachines(final Callback<List<Machines>> callback) {

        mApiService.getMachines().enqueue(new retrofit2.Callback<List<Machines>>() {
            @Override
            public void onResponse(Call<List<Machines>> call, retrofit2.Response<List<Machines>> response) {
                sendCallBack(callback, response);
            }

            @Override
            public void onFailure(Call<List<Machines>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getFeaturedProductsList(String selectedMachine, final Callback<Featured> callback) {

        mApiService.getFeaturedProductsList(selectedMachine).enqueue(new retrofit2.Callback<Featured>() {
            @Override
            public void onResponse(Call<Featured> call, retrofit2.Response<Featured> response) {
                sendCallBack(callback, response);
            }

            @Override
            public void onFailure(Call<Featured> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getListFavorites(final Callback<List<Favorites>> callback) {
        mApiService.getListFavorites().enqueue(new retrofit2.Callback<List<Favorites>>() {
            @Override
            public void onResponse(Call<List<Favorites>> call, retrofit2.Response<List<Favorites>> response) {
                sendCallBack(callback, response);
            }

            @Override
            public void onFailure(Call<List<Favorites>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void buyProductByID(String machineID,String id, final Callback<Amount> callback) {
        mApiService.buyProductByID(machineID, id).enqueue(new retrofit2.Callback<Amount>() {
            @Override
            public void onResponse(Call<Amount> call, retrofit2.Response<Amount> response) {
                sendCallBack(callback, response);
            }

            @Override
            public void onFailure(Call<Amount> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void addProductToFavorites(int id, final Callback<Void> callback) {
        mApiService.addProductToFavorites(id).enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                sendCallBack(callback, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void removeFromFavorites(String id, final Callback<Void> callback) {
        mApiService.removeFromFavorites(id).enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                sendCallBack(callback, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getPurchaseHistory(final Callback<List<History>> callback) {
        mApiService.getPurchaseHistory().enqueue(new retrofit2.Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, retrofit2.Response<List<History>> response) {
                sendCallBack(callback, response);
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
