package com.softjourn.sj_coin.presenters;

import android.util.Log;

import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.callbacks.OnCallEvent;
import com.softjourn.sj_coin.callbacks.OnConcreteMachineReceived;
import com.softjourn.sj_coin.callbacks.OnMachinesListReceived;
import com.softjourn.sj_coin.callbacks.OnProductsListReceived;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendingMachinePresenter extends BasePresenter implements IVendingMachinePresenter, Constants {

    public VendingMachinePresenter() {
    }

    @Override
    public void callMachinesList() {
        createApiManager();

        Callback<List<Machines>> callback = new Callback<List<Machines>>() {
            @Override
            public void onResponse(Call<List<Machines>> call, Response<List<Machines>> response) {
                if (!response.isSuccessful()) {
                    Log.d("Tag", "" + response.code());
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                    mEventBus.post(new OnCallEvent(CALL_FAILED));
                } else {
                    Log.d("Tag", response.body().toString());
                    List<Machines> machines = response.body();
                    mEventBus.post(new OnMachinesListReceived(machines));
                    mEventBus.post(new OnCallEvent(CALL_SUCCEED));
                }
            }

            @Override
            public void onFailure(Call<List<Machines>> call, Throwable t) {
                mEventBus.post(new OnCallEvent(CALL_FAILED));
            }
        };
        mApiProvider.getMachines(callback);
    }

    @Override
    public void callConcreteMachine(String machineID) {
        createApiManager();

        Callback<Machines> callback = new Callback<Machines>() {
            @Override
            public void onResponse(Call<Machines> call, Response<Machines> response) {
                if (!response.isSuccessful()) {
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                    mEventBus.post(new OnCallEvent(CALL_FAILED));
                } else {
                    Machines machines = response.body();
                    mEventBus.post(new OnConcreteMachineReceived(machines));
                }
            }

            @Override
            public void onFailure(Call<Machines> call, Throwable t) {
                mEventBus.post(new OnCallEvent(CALL_FAILED));
            }
        };
        mApiProvider.getConcreteMachine(machineID, callback);
    }

    @Override
    public void callProductsList(String machineID) {
        createApiManager();

        Callback<List<Product>> callback = new Callback<List<Product>>() {

            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    Log.d("Tag", "" + response.code());
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                    mEventBus.post(new OnCallEvent(CALL_FAILED));
                } else {
                    Log.d("Tag", response.body().toString());
                    List<Product> products = response.body();
                    mEventBus.post(new OnProductsListReceived(products));
                    mEventBus.post(new OnCallEvent(CALL_SUCCEED));
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                mEventBus.post(new OnCallEvent(CALL_FAILED));
            }

        };
        mApiProvider.getProductsList(machineID, callback);
    }

    @Override
    public void createApiManager() {
        mApiProvider = ApiManager.getInstance().getVendingProcessApiProvider();
    }
}
