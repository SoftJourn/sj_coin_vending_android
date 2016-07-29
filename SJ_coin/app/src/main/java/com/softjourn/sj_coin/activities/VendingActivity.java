package com.softjourn.sj_coin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;

public class VendingActivity extends AppCompatActivity implements Constants{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (TextUtils.isEmpty(Preferences.retrieveObject(ACCESS_TOKEN))){
            Navigation.goToLoginActivity(this);
        }
    }
}
