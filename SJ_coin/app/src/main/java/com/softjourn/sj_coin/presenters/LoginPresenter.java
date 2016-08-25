package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.MVPmodels.LoginModel;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnLoginCallEvent;
import com.softjourn.sj_coin.contratcts.LoginContract;
import com.softjourn.sj_coin.utils.Connections;

import org.greenrobot.eventbus.Subscribe;

public class LoginPresenter extends BasePresenterImpl implements LoginContract.Presenter{

    private LoginContract.View mLoginView;
    private LoginContract.Model mModel;

    public LoginPresenter(LoginContract.View loginView) {

        onCreate();

        this.mLoginView = loginView;
        this.mModel = new LoginModel();
    }

    public LoginPresenter() {

        onCreate();

        this.mModel = new LoginModel();
    }

    @Override
    public void login(String userName, String password) {
        if (mLoginView != null) {
            mLoginView.showProgress(App.getContext().getString(R.string.progress_authenticating));
        }

        if (validateCredentials(userName, password)) {
            if (makeNetworkChecking()) {
                mModel.makeLoginCall(userName, password);
            } else {
                mLoginView.showNoInternetError();
            }
        }
    }

    @Override
    public void refreshToken(String refreshToken) {
            mModel.makeRefreshToken(refreshToken);
    }

    @Override
    public boolean validateCredentials(String userName, String password) {
        boolean valid = true;

        if (userName.isEmpty()) {
            mLoginView.setUsernameError();
            valid = false;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mLoginView.setPasswordError();
            valid = false;
        }
        return valid;
    }

    @Override
    public boolean makeNetworkChecking() {
        return Connections.isNetworkEnabled();
    }

    @Subscribe
    public void onEvent(OnLoginCallEvent event) {
        if (event.isSuccess()) {
            mLoginView.hideProgress();
            mLoginView.navigateToMain();
        } else {
            mLoginView.hideProgress();
            mLoginView.showToastMessage();
        }
    }
}
