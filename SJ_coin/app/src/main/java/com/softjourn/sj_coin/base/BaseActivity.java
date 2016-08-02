package com.softjourn.sj_coin.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.utils.Constants;
import com.softjourn.sj_coin.utils.ServerErrors;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Ad1 on 02.08.2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements Constants {

    public EventBus mEventBus;

    {
        mEventBus = EventBus.getDefault();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mEventBus.register(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!mEventBus.isRegistered(this)){
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
        if(mEventBus.isRegistered(this)){
            mEventBus.unregister(this);
        }
    }

    @Subscribe
    public void onEvent(final OnServerErrorEvent event){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.getContext(), ServerErrors.showErrorMessage(event.getMessage()),Toast.LENGTH_LONG).show();
            }
        });
    }


}
