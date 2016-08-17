package com.softjourn.sj_coin.activities;

import android.os.Bundle;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.activities.fragments.SeeAllProductsFragment;
import com.softjourn.sj_coin.base.BaseActivity;
import com.softjourn.sj_coin.callbacks.OnTokenRefreshed;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Extras;
import com.softjourn.sj_coin.utils.Preferences;

import org.greenrobot.eventbus.Subscribe;

import java.util.Date;

public class SeeAllActivity extends BaseActivity implements Constants, Extras {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_all);

        if (!isInternetAvailable()) {
            onNoInternetAvailable();
            finish();
        } else {
            if (new Date().getTime() / 1000 >= Long.parseLong(Preferences.retrieveStringObject(EXPIRATION_DATE))) {
                callRefreshToken();
            } else {

                setTitle(getIntent().getStringExtra(EXTRAS_CATEGORY));

                attachFragment();
            }
        }
    }

    private void attachFragment(){
        this.getFragmentManager().beginTransaction()
                .replace(R.id.container_for_see_all_products, SeeAllProductsFragment.newInstance(), TAG_PRODUCTS_SEE_ALL_LAST_PURCHASES_FRAGMENT)
                .commit();
    }

    @Subscribe
    public void OnEvent(OnTokenRefreshed event) {
        if (event.isSuccess()) {
            onCallSuccess();
            attachFragment();
        } else {
            onCallFailed();
        }
    }
}
