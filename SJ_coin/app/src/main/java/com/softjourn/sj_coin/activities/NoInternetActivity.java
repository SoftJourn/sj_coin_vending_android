package com.softjourn.sj_coin.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.utils.Const;

import butterknife.Bind;


public class NoInternetActivity extends AppCompatActivity implements Const {

    private BroadcastListener mBroadcastListener;

    @Bind(R.id.imageView)
    ImageView imageview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet_activity);
        mBroadcastListener = new BroadcastListener();

        registerReceiver(mBroadcastListener,new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastListener);
        super.onDestroy();
    }

    private class BroadcastListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getExtras() != null) {
                final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo ni = connectivityManager.getActiveNetworkInfo();

                if (ni != null && ni.isConnectedOrConnecting()) {
                    Intent intentToStart = new Intent(NoInternetActivity.this, SplashActivity.class);
                    startActivity(intentToStart);
                    finish();
                }
            }
        }
    }
}
