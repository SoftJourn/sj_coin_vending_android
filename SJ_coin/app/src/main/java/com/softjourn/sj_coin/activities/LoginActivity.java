package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.callbacks.OnLogin;
import com.softjourn.sj_coin.presenters.ILoginSessionPresenter;
import com.softjourn.sj_coin.presenters.LoginSessionPresenter;
import com.softjourn.sj_coin.utils.Connections;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ad1 on 28.07.2016.
 */
public class LoginActivity extends BaseActivity implements Constants{

    private static final String TAG = "LoginActivity";
    @Bind(R.id.input_email)
    EditText emailText;
    @Bind(R.id.input_password)
    EditText passwordText;
    @Bind(R.id.btn_login)
    Button loginButton;
    private ILoginSessionPresenter mPresenter;

    @OnClick(R.id.btn_login)
    public void loginProcess(){
        if (Connections.isNetworkEnabled()) {
            login();
        } else {
            Toast.makeText(this,"Internet should be turned on",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);
        ProgressDialogUtils.showDialog(this,"Authenticating...");

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        mPresenter = new LoginSessionPresenter(email,password);
        mPresenter.callLogin();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Subscribe
    public void onEvent(OnLogin event) {
        if(event.isSuccess()){
            onLoginSuccess();
        } else {
            onLoginFailed();
        }
    }

    public void onLoginSuccess() {
        ProgressDialogUtils.dismiss();
        Navigation.goToVendingActivity(LoginActivity.this);
        finish();
    }

    public void onLoginFailed() {
        ProgressDialogUtils.dismiss();
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() ) {
            emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("Password should be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }
        return valid;
    }
}
