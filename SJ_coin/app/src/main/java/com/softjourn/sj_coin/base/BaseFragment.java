package com.softjourn.sj_coin.base;


import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.softjourn.sj_coin.App;
import com.softjourn.sj_coin.callbacks.OnServerErrorEvent;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;
import com.softjourn.sj_coin.utils.ServerErrors;

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
    public void onEvent(final OnServerErrorEvent event) {
        Toast.makeText(App.getContext(), ServerErrors.showErrorMessage(event.getMessage()), Toast.LENGTH_LONG).show();
    }

}
