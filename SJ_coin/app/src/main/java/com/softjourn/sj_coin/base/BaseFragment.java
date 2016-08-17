package com.softjourn.sj_coin.base;


import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.softjourn.sj_coin.callbacks.OnCallEvent;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public abstract class BaseFragment extends Fragment {

    public static EventBus mEventBus = EventBus.getDefault();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEventBus.register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mEventBus.isRegistered(this)) {
            mEventBus.register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mEventBus.unregister(this);
    }

    public void onCallSuccess() {
        ProgressDialogUtils.dismiss();
    }

    public void onCallFailed() {
        ProgressDialogUtils.dismiss();
    }

    @Subscribe
    public void onEvent(final OnCallEvent event){
        if (event.isSuccess()) {
            onCallSuccess();
        } else {
            onCallFailed();
        }
    }

}
