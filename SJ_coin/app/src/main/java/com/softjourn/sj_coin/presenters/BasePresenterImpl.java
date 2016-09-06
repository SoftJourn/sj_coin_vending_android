package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;

public class BasePresenterImpl implements BasePresenter{

    public EventBus mEventBus = EventBus.getDefault();

    @Override
    public void onCreate() {
        mEventBus.register(this);
    }

    @Override
    public void onDestroy() {
            mEventBus.unregister(this);
    }

    /**
     * Needs to be overriden in child Classes
     */

    @Override
    public void refreshToken(String refreshToken) {
    }

    /**
     * Needs to be overriden in child Classes
     */
    @Override
    public boolean makeNetworkChecking() {
        return false;
    }

}
