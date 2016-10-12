package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.History;

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
