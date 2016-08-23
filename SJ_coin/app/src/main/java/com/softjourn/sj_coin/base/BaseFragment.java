package com.softjourn.sj_coin.base;


import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.softjourn.sj_coin.R;
import com.softjourn.sj_coin.utils.ProgressDialogUtils;
import com.softjourn.sj_coin.utils.Utils;

import org.greenrobot.eventbus.EventBus;

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

    protected void onNoInternetAvailable() {
        showToast(getString(R.string.internet_turned_off));
        ProgressDialogUtils.dismiss();
    }

    public void showToast(String text) {
        Utils.showErrorToast(getActivity(), text, Gravity.CENTER);
    }

    public void hideProgress(){ProgressDialogUtils.dismiss();}
}
