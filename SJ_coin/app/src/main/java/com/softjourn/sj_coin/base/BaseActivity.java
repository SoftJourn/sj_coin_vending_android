package com.softjourn.sj_coin.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.Preferences;
import com.softjourn.sj_coin.utils.ServerErrors;
import com.softjourn.sj_coin.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.NoSubscriberEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseActivity extends AppCompatActivity implements Constants {

    public EventBus mEventBus = EventBus.getDefault();

    protected boolean mProfileIsVisible = false;

    ProgressDialog mProgressDialog;

    public BottomBar mBottomBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventBus.register(this);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setTextAppearance(R.style.BottomBarItem);
        mBottomBar.setItems(R.menu.bottombar_menu);    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mEventBus.isRegistered(this)) {
            mEventBus.register(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mEventBus.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mEventBus.isRegistered(this)) {
            mEventBus.unregister(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                if (!mProfileIsVisible) {
                    Navigation.goToProfileActivity(this);
                    return true;
                } else {
                    return false;
                }
            case R.id.logout:
                Preferences.clearStringObject(ACCESS_TOKEN);
                Preferences.clearStringObject(REFRESH_TOKEN);
                Navigation.goToLoginActivity(this);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void showProgress(String message) {
        mProgressDialog = new ProgressDialog(this, R.style.Base_V7_Theme_AppCompat_Dialog);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void onNoInternetAvailable() {
        showToast(getString(R.string.internet_turned_off));
        hideProgress();
    }

    public void showToast(String text) {
        Utils.showErrorToast(this, text, Gravity.CENTER);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final OnServerErrorEvent event) {
        showToast(ServerErrors.showErrorMessage(event.getMessage()));
    }

    @Subscribe
    public void OnEvent(NoSubscriberEvent event) {
        hideProgress();
    }
}
