package com.softjourn.sj_coin.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.Navigation;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;
import com.softjourn.sj_coin.utils.ServerErrors;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Ad1 on 02.08.2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements Constants {

    public static Activity mActivity;
    public EventBus mEventBus;

    {
        mEventBus = EventBus.getDefault();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventBus.register(this);
    }

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
            case R.id.list_view:
                try {
                    android.app.Fragment fragment = this.getFragmentManager().findFragmentByTag(TAG_PRODUCTS_LIST_FRAGMENT);
                    if (fragment == null) {
                        Navigation.goToListView(mActivity);
                        return true;
                    } else return false;
                } catch (NullPointerException e) {
                    return false;
                }
            case R.id.machine_view:
                try {
                    android.app.Fragment machineFragment = this.getFragmentManager().findFragmentByTag(TAG_PRODUCTS_MACHINE_FRAGMENT);
                    if (machineFragment == null) {
                        Navigation.goToMachineView(mActivity);
                        return true;
                    } else return false;
                } catch (NullPointerException e) {
                    return false;
                }
            case R.id.profile:
                Navigation.goToProfileActivity(this);
                return true;
            case R.id.logout:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCallSuccess() {
        ProgressDialogUtils.dismiss();
    }

    public void onCallFailed() {
        ProgressDialogUtils.dismiss();
    }


    @Subscribe
    public void onEvent(final OnServerErrorEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.getContext(), ServerErrors.showErrorMessage(event.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
    }


}
