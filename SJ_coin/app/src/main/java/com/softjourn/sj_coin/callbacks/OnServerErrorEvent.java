package com.softjourn.sj_coin.callbacks;

public class OnServerErrorEvent {
    private String message;

    public OnServerErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
