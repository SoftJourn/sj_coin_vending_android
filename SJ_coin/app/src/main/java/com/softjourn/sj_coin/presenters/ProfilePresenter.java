package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.contratcts.ProfileContract;
import com.softjourn.sj_coin.events.OnAccountReceivedEvent;
import com.softjourn.sj_coin.events.OnAmountReceivedEvent;
import com.softjourn.sj_coin.events.OnBalanceUpdatedEvent;
import com.softjourn.sj_coin.events.OnHistoryReceived;
import com.softjourn.sj_coin.events.OnTokenRevoked;
import com.softjourn.sj_coin.mvpmodels.ProfileModel;
import com.softjourn.sj_coin.mvpmodels.VendingModel;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.NetworkUtils;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.UIUtils;

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
    }

    @Override
    public void getAccount() {
        if (!NetworkUtils.isNetworkEnabled()) {
            mView.showNoInternetError();
        } else {
            mProfileModel.makeAccountCall();
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
    public void cancelRunningRequest() {
        mProfileModel.cancelRequest();
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

