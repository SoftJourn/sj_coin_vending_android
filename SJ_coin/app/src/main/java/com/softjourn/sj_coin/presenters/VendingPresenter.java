package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.MVPmodels.VendingModel;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.contratcts.VendingContract;
import com.softjourn.sj_coin.utils.Connections;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Preferences;

import org.greenrobot.eventbus.Subscribe;

import java.util.Date;

public class VendingPresenter extends BasePresenterImpl implements VendingContract.Presenter, Constants {

    private VendingContract.View mView;
    private VendingContract.Model mModel;
    private LoginPresenter mLoginPresenter;

    private String mMachineID;

    public VendingPresenter(VendingContract.View vendingView) {

        onCreate();

        this.mView = vendingView;
        this.mModel = new VendingModel();
        this.mLoginPresenter = new LoginPresenter();
    }


    @Override
    public void getProductList(String machineID) {

        mMachineID = machineID;

        if (!makeNetworkChecking()) {
            mView.showNoInternetError();
        } else {
            if (checkExpirationDate()) {
                mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Constants.REFRESH_TOKEN);
            } else {
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mModel.callProductsList(mMachineID);
            }
        }
    }

    @Override
    public boolean checkExpirationDate() {
        return (new Date().getTime() / 1000 >= Long.parseLong(Preferences.retrieveStringObject(EXPIRATION_DATE)));
    }

    @Override
    public void refreshToken(String refreshToken) {
        mLoginPresenter.refreshToken(refreshToken);
    }

    @Override
    public boolean makeNetworkChecking() {
        return Connections.isNetworkEnabled();
    }

    @Subscribe
    public void OnEvent(OnTokenRefreshed event) {
        if (event.isSuccess()) {
            mView.hideProgress();
            mModel.callProductsList(mMachineID);
        } else {
            mView.hideProgress();
        }
    }

    /*@Subscribe
    public void OnEvent(OnProductsListReceived event) {
        mView.hideProgress();
        mView.loadData(event.getProductsList());
    }*/
}
