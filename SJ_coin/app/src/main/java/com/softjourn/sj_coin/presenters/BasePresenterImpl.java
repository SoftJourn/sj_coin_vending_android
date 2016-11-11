package com.softjourn.sj_coin.presenters;

import com.softjourn.sj_coin.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;

public class BasePresenterImpl implements BasePresenter{

    private final EventBus mEventBus = EventBus.getDefault();

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

    @Override
    public void logOut(String refreshToken) {
    }
}
