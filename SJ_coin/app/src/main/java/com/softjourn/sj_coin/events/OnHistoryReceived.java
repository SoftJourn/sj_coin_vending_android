package com.softjourn.sj_coin.events;

import com.softjourn.sj_coin.api.models.History;

import java.util.List;

public class OnHistoryReceived {

    private final List<History> mHistory;

    public OnHistoryReceived (List<History> history) {
        this.mHistory = history;
    }

    public List<History> getHistoryList(){
        return mHistory;
    }
}
