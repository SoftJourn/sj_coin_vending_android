package com.softjourn.sj_coin.presenters;

import com.google.gson.Gson;
import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.api.models.accountInfo.Cash;
import com.softjourn.sj_coin.contratcts.ProfileContract;
import com.softjourn.sj_coin.events.OnAccountReceivedEvent;
import com.softjourn.sj_coin.events.OnAmountReceivedEvent;
import com.softjourn.sj_coin.events.OnBalanceUpdatedEvent;
import com.softjourn.sj_coin.events.OnHistoryReceived;
import com.softjourn.sj_coin.events.OnTokenRefreshed;
import com.softjourn.sj_coin.events.OnTokenRevoked;
import com.softjourn.sj_coin.mvpmodels.ProfileModel;
import com.softjourn.sj_coin.mvpmodels.VendingModel;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.NetworkUtils;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.ServerErrors;
import com.softjourn.sj_coin.utils.UIUtils;
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
        if (!NetworkUtils.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            if (Utils.checkExpirationDate()) {
                //mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Preferences.retrieveStringObject(REFRESH_TOKEN));
            } else {
                //mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mProfileModel.makeAccountCall();
            }
        }
    }

    @Override
    public void logOut(String refreshToken) {
        if (!NetworkUtils.isNetworkEnabled()) {
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
    public void addMoney(String fromCode) {
        if (!NetworkUtils.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            if (Utils.checkExpirationDate()) {
                //mView.showProgress(App.getContext().getString(R.string.progress_authenticating));
                refreshToken(Preferences.retrieveStringObject(REFRESH_TOKEN));
            } else {
                Cash cash = new Gson().fromJson(fromCode, Cash.class);
                mView.showProgress(App.getContext().getString(R.string.progress_loading));
                mProfileModel.putMoneyToWallet(cash);
            }
        }
    }

    @Override
    public void cancelRunningRequest() {
        mProfileModel.cancelRequest();
    }

    @Override
    public void refreshToken(String refreshToken) {
        mLoginPresenter.refreshToken(refreshToken);
    }

    @Subscribe
    public void OnEvent(OnAccountReceivedEvent event) {
        String userName = UIUtils.getUserFullName(event.getAccount().getName(), event.getAccount().getSurname());
        mView.hideProgress();
        mView.showBalance(event.getAccount().getAmount());
        if (Preferences.retrieveStringObject(USER_NAME_PREFERENCES_KEY) == null ||
                !Preferences.retrieveStringObject(USER_NAME_PREFERENCES_KEY).equals(userName)) {
            mView.setUserName(userName);
        }
        showHistory();
    }

    @Subscribe
    public void OnEvent(OnAmountReceivedEvent event) {
        mView.showBalance(String.valueOf(event.getAmount().getAmount()));
    }

    @Subscribe
    public void OnEvent(OnBalanceUpdatedEvent event) {
        mView.hideProgress();
        mView.showBalance(event.getBalance());
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

