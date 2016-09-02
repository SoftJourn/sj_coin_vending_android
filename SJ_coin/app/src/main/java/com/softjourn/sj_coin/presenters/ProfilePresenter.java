package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.MVPmodels.ProfileModel;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnAccountReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.contratcts.ProfileContract;
import com.softjourn.sj_coin.utils.Connections;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Preferences;

import org.greenrobot.eventbus.Subscribe;

import java.util.Date;

public class ProfilePresenter extends BasePresenterImpl implements ProfileContract.Presenter, Constants {

    private ProfileContract.View mView;
    private ProfileContract.Model mModel;
    private LoginPresenter mLoginPresenter;

    public ProfilePresenter(ProfileContract.View profileView){

        onCreate();

        this.mView = profileView;
        this.mModel = new ProfileModel();
        this.mLoginPresenter = new LoginPresenter();
    }

    @Override
    public void getAccount() {
        if (!makeNetworkChecking()) {
            mView.showNoInternetError();
        } else {
            if (checkExpirationDate()) {
                mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Constants.REFRESH_TOKEN);
            } else {
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mModel.makeAccountCall();
            }
        }
    }

    @Override
    public void getBalance() {
        if (!makeNetworkChecking()) {
            mView.showNoInternetError();
        } else {
            if (checkExpirationDate()) {
                refreshToken(Constants.REFRESH_TOKEN);
            } else {
                mModel.makeBalanceCall();
            }
        }
    }

    @Override
    public void getHistory() {
        mView.setData(mModel.loadHistory());
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
    public void OnEvent(OnAccountReceivedEvent event) {
        mView.hideProgress();
        mView.showBalance(event.getAccount());
        mView.setUserName(event.getAccount().getName()+" "+event.getAccount().getSurname());
    }

    @Subscribe
    public void OnEvent(OnTokenRefreshed event) {
        if (event.isSuccess()) {
            mView.hideProgress();
            mModel.makeAccountCall();
        } else {
            mView.hideProgress();
        }
    }
}

