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

import com.jrummyapps.android.widget.AnimatedSvgView;
import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.utils.Const;


public class NoInternetActivity extends AppCompatActivity implements Const {

    private BroadcastListener mBroadcastListener;

    private AnimatedSvgView svgView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet_activity);
        mBroadcastListener = new BroadcastListener();

        //Draw svg image
        svgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);
        svgView.postDelayed(new Runnable() {

            @Override public void run() {
                svgView.start();
            }
        }, 500);
        svgView.start();

        registerReceiver(mBroadcastListener,new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastListener);
        super.onDestroy();
    }

    /**
     * Listener for Internet state. If Application was launched with no Internet
     * and internet state with changed to true start Launcher activity.
     */
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
