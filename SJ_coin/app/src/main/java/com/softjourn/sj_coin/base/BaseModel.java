package com.softjourn.sj_coin.base;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseModel {

    protected final EventBus mEventBus = EventBus.getDefault();

}
