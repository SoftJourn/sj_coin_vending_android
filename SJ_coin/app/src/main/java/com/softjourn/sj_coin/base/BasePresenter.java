package com.softjourn.sj_coin.base;

import com.softjourn.sj_coin.api.ApiProvider;

import org.greenrobot.eventbus.EventBus;

public abstract class BasePresenter{

    protected ApiProvider mApiProvider;
    protected EventBus mEventBus = EventBus.getDefault();


    public abstract void createApiManager();
}
