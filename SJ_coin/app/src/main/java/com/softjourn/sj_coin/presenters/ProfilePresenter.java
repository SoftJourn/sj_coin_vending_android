package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.MVPmodels.ProfileModel;
import com.softjourn.sj_coin.MVPmodels.VendingModel;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnAccountReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnAmountReceivedEvent;
import com.softjourn.sj_coin.callbacks.OnHistoryReceived;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.callbacks.OnTokenRevoked;
import com.softjourn.sj_coin.contratcts.ProfileContract;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.NetworkManager;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.ServerErrors;
import com.softjourn.sj_coin.utils.Utils;

import org.greenrobot.eventbus.Subscribe;

public class ProfilePresenter extends BasePresenterImpl implements ProfileContract.Presenter, Const {

    private final ProfileContract.View mView;
    private final ProfileModel mProfileModel;
    private final LoginPresenter mLoginPresenter;
    private final VendingModel mVendingModel;

    public ProfilePresenter(ProfileContract.View profileView) {

        onCreate();

        mView = profileView;
        mProfileModel = new ProfileModel();
        mVendingModel = new VendingModel();
        mLoginPresenter = new LoginPresenter();
        //mView.showProgress(App.getContext().getString(R.string.progress_loading));
    }

    @Override
    public void getAccount() {
        if (!NetworkManager.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            if (Utils.checkExpirationDate()) {
                //mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Preferences.retrieveStringObject(REFRESH_TOKEN));
            } else {
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mProfileModel.makeAccountCall();
            }
        }
    }

    @Override
    public void logOut(String refreshToken) {
        if (!NetworkManager.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            mView.showProgress(App.getContext().getString(R.string.progress_loading));
            mLoginPresenter.logOut(refreshToken);
        }
    }

    @Override
    public void showHistory() {
        mVendingModel.getPurchaseHistory();
    }

    @Override
    public void refreshToken(String refreshToken) {
        mLoginPresenter.refreshToken(refreshToken);
    }

    @Subscribe
    public void OnEvent(OnAccountReceivedEvent event) {
        mView.hideProgress();
        mView.showBalance(event.getAccount().getAmount());
        mView.setUserName(event.getAccount().getName() + " " + event.getAccount().getSurname());
        showHistory();
    }

    @Subscribe
    public void OnEvent(OnAmountReceivedEvent event) {
        mView.showBalance(String.valueOf(event.getAmount().getAmount()));
    }

    @Subscribe
    public void OnEvent(OnTokenRefreshed event) {
        if (event.isSuccess()) {
            mView.hideProgress();
            mProfileModel.makeAccountCall();
        } else {
            mView.onCreateErrorDialog(ServerErrors.showErrorMessage("default"));
            mView.hideProgress();
        }
    }

    @Subscribe
    public void OnEvent(OnHistoryReceived event) {
        mView.setData(event.getHistoryList());
        mView.hideProgress();
    }

    @Subscribe
    public void OnEvent(OnTokenRevoked event) {
            mView.hideProgress();
            mView.logOut();
    }
}

