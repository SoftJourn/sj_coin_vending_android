package com.softjourn.sj_coin.events;

public class OnServerErrorEvent {

    private final String message;

    public OnServerErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
