package com.softjourn.sj_coin.MVPmodels;


import com.softjourn.sj_coin.ProductsListSingleton;
import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.api.vending.VendingApiProvider;
import com.softjourn.sj_coin.base.BaseModel;
import com.softjourn.sj_coin.callbacks.OnBoughtEvent;
import com.softjourn.sj_coin.callbacks.OnMachinesListReceived;
import com.softjourn.sj_coin.callbacks.OnProductsListReceived;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.model.TransactionResponse;
import com.softjourn.sj_coin.model.machines.Machines;
import com.softjourn.sj_coin.model.products.Product;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendingModel extends BaseModel implements VendingContract.Model,Constants {

    private VendingApiProvider mApiProvider;

    @Override
    public void callMachinesList() {

        createApiManager();

        Callback<List<Machines>> callback = new Callback<List<Machines>>() {
            @Override
            public void onResponse(Call<List<Machines>> call, Response<List<Machines>> response) {
                if (!response.isSuccessful()) {
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                } else {
                    List<Machines> machines = response.body();
                    mEventBus.post(new OnMachinesListReceived(machines));
                }
            }

            @Override
            public void onFailure(Call<List<Machines>> call, Throwable t) {
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
                } else {
                    Utils.storeConcreteMachineInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<Machines> call, Throwable t) {

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
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                } else {
                    List<Product> products = response.body();
                    mEventBus.post(new OnProductsListReceived(products));
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }

        };
        mApiProvider.getProductsList(machineID, callback);
    }

    @Override
    public List<Product> loadLocalProductList() {
        return ProductsListSingleton.getInstance().getData();
    }

    @Override
    public void buyProductByID(String id) {
        createApiManager();

        Callback<TransactionResponse> callback = new Callback<TransactionResponse>() {

            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                if (!response.isSuccessful()) {
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                } else {
                    mEventBus.post(new OnBoughtEvent(CALL_SUCCEED));
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                mEventBus.post(new OnBoughtEvent(CALL_FAILED));
            }
        };
        mApiProvider.buyProductByID(id, callback);
    }

    public void createApiManager() {
        mApiProvider = ApiManager.getInstance().getVendingProcessApiProvider();
    }

}
