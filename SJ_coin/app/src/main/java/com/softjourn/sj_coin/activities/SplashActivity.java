package com.softjourn.sj_coin.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.softjourn.sj_coin.utils.Const;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;

public class SplashActivity extends AppCompatActivity implements Const {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (TextUtils.isEmpty(Preferences.retrieveStringObject(ACCESS_TOKEN))
                && TextUtils.isEmpty(Preferences.retrieveStringObject(REFRESH_TOKEN))) {
            Navigation.goToLoginActivity(this);
            finish();
        } else {
            Navigation.goToVendingActivity(this);
            finish();
        }
    }
}
