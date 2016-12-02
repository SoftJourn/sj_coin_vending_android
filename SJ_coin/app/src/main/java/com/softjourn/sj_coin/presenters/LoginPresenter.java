package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.contratcts.LoginContract;
import com.softjourn.sj_coin.events.OnLoginCallEvent;
import com.softjourn.sj_coin.mvpmodels.LoginModel;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.NetworkUtils;
import com.softjourn.sj_coin.utils.Preferences;

import org.greenrobot.eventbus.Subscribe;

public class LoginPresenter extends BasePresenterImpl implements LoginContract.Presenter {

    private LoginContract.View mLoginView;
    private final LoginModel mModel;

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

        if (validateCredentials(userName, password)) {
            mLoginView.showProgress(App.getContext().getString(R.string.progress_authenticating));
            if (NetworkUtils.isNetworkEnabled()) {
                mModel.makeLoginCall(userName, password);
            } else {
                mLoginView.showNoInternetError();
            }
        }
    }

    @Override
    public void logOut(String refreshToken) {
            mModel.revokeRefreshToken(refreshToken);
    }

    @Override
    public void refreshToken(String refreshToken) {
        mModel.makeRefreshToken(Preferences.retrieveStringObject(Const.REFRESH_TOKEN));
    }

    public boolean validateCredentials(String userName, String password) {

        if (password.isEmpty() && userName.isEmpty()) {
            mLoginView.setUsernameError();
            return false;
        }

        if (userName.isEmpty()|| !userName.matches("[a-z]+")) {
            mLoginView.setUsernameError();
            return false;
        }

        if (password.isEmpty()) {
            mLoginView.setPasswordError();
            return false;
        }

        return true;
    }

    @Subscribe
    public void onEvent(OnLoginCallEvent event) {
        if (event.isSuccess()) {
            mLoginView.hideProgress();
            mLoginView.navigateToMain();
        } else {
            mLoginView.hideProgress();
            mLoginView.showToastMessage(App.getContext().getString(R.string.activity_login_login_failed));
        }
    }
}
