package com.softjourn.sj_coin.base;

import com.softjourn.sj_coin.api.ApiManager;
import com.softjourn.sj_coin.api.ApiProvider;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Ad1 on 02.08.2016.
 */
public class BasePresenter {

    protected ApiProvider mApiProvider;
    protected EventBus mEventBus;


    public void createApiManager(String inHeaders, String inUrl)
    {
        mApiProvider = ApiManager.getInstance(inHeaders,inUrl).getApiProvider();
        mEventBus = EventBus.getDefault();
    }
}
