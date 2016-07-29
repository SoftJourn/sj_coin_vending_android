package com.softjourn.sj_coin.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.api.ApiInterface;
import com.softjourn.sj_coin.api.ApiClient;
import com.softjourn.sj_coin.model.Session;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ad1 on 28.07.2016.
 */
public class LoginActivity extends Activity implements Constants{

    private static final String TAG = "LoginActivity";

    @Bind(R.id.input_email)
    EditText emailText;

    @Bind(R.id.input_password)
    EditText passwordText;

    @Bind(R.id.btn_login)
    Button loginButton;

    @Bind(R.id.link_signup)
    TextView signUpLink;

    @OnClick(R.id.btn_login)
    public void loginProcess(){
        login();
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
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        //progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        makeLoginRequest(email,password,GRANT_TYPE);
    }

    public void makeLoginRequest(String email, String password, String type) {

        ApiInterface apiInterface = ApiClient.getOAuthClient().create(ApiInterface.class);
        Call<Session> call = apiInterface.getAccessToken(email,password,type);
        call.enqueue(new Callback<Session>() {
            @Override
            public void onResponse(Call<Session> call, Response<Session> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Not Authenticated",Toast.LENGTH_LONG).show();
                    Log.d("Tag", (String.valueOf(response.code())));
                    loginButton.setEnabled(true);
                } else {
                    Preferences.storeString(ACCESS_TOKEN,response.body().getAccessToken());
                    Preferences.storeString(REFRESH_TOKEN,response.body().getRefreshToken());

                    onLoginSuccess();
                }
            }

            @Override
            public void onFailure(Call<Session> call, Throwable t) {
                Log.d("Tag",t.toString());
                    onLoginFailed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        //progressDialog.dismiss();
        Navigation.goToVendingActivity(LoginActivity.this);
        finish();
    }

    public void onLoginFailed() {
        //progressDialog.dismiss();
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
