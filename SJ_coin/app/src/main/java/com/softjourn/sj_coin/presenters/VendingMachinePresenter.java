package com.softjourn.sj_coin.presenters;

import android.util.Log;

import com.softjourn.sj_coin.base.BasePresenter;
import com.softjourn.sj_coin.callbacks.OnLogin;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.callbacks.OnVendingMachineReceived;
import com.softjourn.sj_coin.model.Machines.Machine;
import com.softjourn.sj_coin.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Ad1 on 02.08.2016.
 */
public class VendingMachinePresenter extends BasePresenter implements IVendingMachinePresenter,Constants{

    private String mMachineID;

    public VendingMachinePresenter(String inMachineID){
        this.mMachineID=inMachineID;
    }

    @Override
    public void callVendingMachine() {

        createApiManager(VENDING,URL_VENDING_SERVICE);

        Callback<Machine> callback = new Callback<Machine>() {

            @Override
            public void onResponse(Call<Machine> call, retrofit2.Response<Machine> response) {
                if (!response.isSuccessful()){
                    Log.d("Tag",""+response.code());
                    mEventBus.post(new OnServerErrorEvent(response.code()));
                    mEventBus.post(new OnLogin(CALL_FAILED));
                } else {
                    Log.d("Tag",response.body().toString());
                    mEventBus.post(new OnVendingMachineReceived(response.body()));
                    mEventBus.post(new OnLogin(CALL_SUCCEED));
                }
            }

            @Override
            public void onFailure(Call<Machine> call, Throwable t) {
                mEventBus.post(new OnLogin(CALL_FAILED));
            }
        };
        mApiProvider.getSelectedMachine(mMachineID,callback);
    }

}
