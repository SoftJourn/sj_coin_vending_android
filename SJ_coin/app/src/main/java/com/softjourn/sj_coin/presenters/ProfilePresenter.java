package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.MVPmodels.ProfileModel;
import com.softjourn.sj_coin.MVPmodels.VendingModel;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnAccountReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnAmountReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnFeaturedProductsListReceived;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.contratcts.ProfileContract;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.NetworkManager;
import com.softjourn.sj_coin.utils.Preferences;

import org.greenrobot.eventbus.Subscribe;

import java.util.Date;

public class ProfilePresenter extends BasePresenterImpl implements ProfileContract.Presenter, Const {

    private ProfileContract.View mView;
    private ProfileModel mProfileModel;
    private VendingModel mVendingModel;
    private LoginPresenter mLoginPresenter;

    public ProfilePresenter(ProfileContract.View profileView) {

        onCreate();

        mView = profileView;
        mProfileModel = new ProfileModel();
        mVendingModel = new VendingModel();
        mLoginPresenter = new LoginPresenter();
        mView.showProgress(App.getContext().getString(R.string.progress_loading));
        mVendingModel.callFeaturedProductsList(Preferences.retrieveStringObject(SELECTED_MACHINE_ID));
    }

    @Override
    public void getAccount() {
        if (!makeNetworkChecking()) {
            mView.showNoInternetError();
        } else {
            if (checkExpirationDate()) {
                mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Preferences.retrieveStringObject(REFRESH_TOKEN));
            } else {
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mProfileModel.makeAccountCall();
            }
        }
    }

    @Override
    public void getBalance() {
        if (!makeNetworkChecking()) {
            mView.showNoInternetError();
        } else {
            if (checkExpirationDate()) {
                refreshToken(Preferences.retrieveStringObject(REFRESH_TOKEN));
            } else {
                mProfileModel.makeBalanceCall();
            }
        }
    }

    @Override
    public void showHistory() {
        //mView.setData(mProfileModel.loadHistory());
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
        return NetworkManager.isNetworkEnabled();
    }

    @Subscribe
    public void OnEvent(OnAccountReceivedEvent event) {
        mView.hideProgress();
        mView.showBalance(event.getAccount().getAmount());
        mView.setUserName(event.getAccount().getName() + " " + event.getAccount().getSurname());
    }

    @Subscribe
    public void OnEvent(OnAmountReceivedEvent event) {
        mView.showBalance(String.format("%d", event.getAmount().getAmount()));
    }

    @Subscribe
    public void OnEvent(OnTokenRefreshed event) {
        if (event.isSuccess()) {
            mView.hideProgress();
            mProfileModel.makeAccountCall();
        } else {
            mView.hideProgress();
        }
    }

    @Subscribe
    public void OnEvent(OnFeaturedProductsListReceived event) {
        mView.hideProgress();
        showHistory();
    }
}

