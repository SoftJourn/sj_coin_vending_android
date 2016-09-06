package com.softjourn.sj_coin.callbacks;

/**
 * Created by Ad1 on 02.08.2016.
 */
public class OnServerErrorEvent {
    private int message;

    public OnServerErrorEvent(int message) {
        this.message = message;
    }

    public int getMessage() {
        return message;
    }
}
