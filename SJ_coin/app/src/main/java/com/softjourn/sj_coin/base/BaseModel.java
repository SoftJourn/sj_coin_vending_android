package com.softjourn.sj_coin.base;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseModel {

    protected EventBus mEventBus = EventBus.getDefault();

}
