package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.contratcts.LoginContract;
import com.softjourn.sj_coin.presenters.LoginPresenter;
import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Navigation;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity implements LoginContract.View, Const {

    @Bind(R.id.input_email)
    EditText mUserName;
    @Bind(R.id.input_password)
    EditText mPasswordText;
    @Bind(R.id.btn_login)
    Button mLoginButton;

    private LoginContract.Presenter mPresenter;

    @OnClick(R.id.btn_login)
    public void loginProcess() {
        login();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPresenter = new LoginPresenter(this);

    }

    public void login() {

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
        Navigation.goToVendingActivity(LoginActivity.this);
        finish();
    }

    @Override
    public void showToastMessage(String message) {
        showToast(message);
    }

    @Override
    public void showNoInternetError() {
        onNoInternetAvailable();
    }
}
