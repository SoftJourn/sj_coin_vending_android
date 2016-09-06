package com.softjourn.sj_coin.callbacks;

public class OnServerErrorEvent {
    private int message;

    public OnServerErrorEvent(int message) {
        this.message = message;
    }

    public int getMessage() {
        return message;
    }
}
