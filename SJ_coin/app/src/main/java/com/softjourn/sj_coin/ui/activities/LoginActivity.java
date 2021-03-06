package com.softjourn.sj_coin.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.contratcts.LoginContract;
import com.softjourn.sj_coin.events.OnServerErrorEvent;
import com.softjourn.sj_coin.presenters.LoginPresenter;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.ServerErrors;
import com.softjourn.sj_coin.utils.Utils;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity implements LoginContract.View, Const {

    @BindView(R.id.input_email)
    EditText mUserName;
    @BindView(R.id.input_password)
    EditText mPasswordText;
    @BindView(R.id.btn_login)
    Button mLoginButton;
    @BindView(R.id.login_root)
    LinearLayout mLinearLayout;
    @BindView(R.id.link_to_welcome_activity)
    ImageView mArrowToWelcome;

    private LoginContract.Presenter mPresenter;

    @OnClick(R.id.btn_login)
    public void loginProcess() {
        login();
    }

    @OnClick(R.id.link_to_welcome_activity)
    public void goToWelcome() {
        Navigation.goToWelcomeActivity(this);
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPresenter = new LoginPresenter(this);

        if (Preferences.retrieveBooleanObject(Const.IS_FIRST_TIME_LAUNCH)) {
            Navigation.goToWelcomeActivity(this);
            this.finish();
        }

    }

    private void login() {
        String userName = mUserName.getText().toString();
        String password = mPasswordText.getText().toString();
        mPresenter.login(userName, password);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void showProgress(String message) {
        super.showProgress(message);
    }

    @Override
    public void showSnackBar(String message) {

    }

    @Override
    public void hideProgress() {
        super.hideProgress();
    }

    @Override
    public void setUsernameError() {
        mUserName.requestFocus();
        mUserName.setError(getString(R.string.activity_login_invalid_email));
    }

    @Override
    public void setPasswordError() {
        mPasswordText.requestFocus();
        mPasswordText.setError(getString(R.string.activity_login_invalid_password));
    }

    @Override
    public void navigateToMain() {
        mPresenter.onDestroy();
        mPresenter = null;
        Navigation.goToVendingActivity(this);
        finish();

    }

    @Override
    public void showToastMessage(String message) {
        Utils.showSnackBar(findViewById(R.id.login_root), message);
        mUserName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        mPasswordText.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }

    @Override
    public void showNoInternetError() {
        onNoInternetAvailable();
    }

    @Subscribe
    public void onEvent(final OnServerErrorEvent event) {
        hideProgress();
        onCreateErrorDialog(ServerErrors.showErrorMessage(event.getMessage()));
    }
}
