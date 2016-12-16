package com.softjourn.sj_coin.events;

import com.softjourn.sj_coin.api.models.Amount;

public class OnAmountReceivedEvent {
    private final Amount amount;

    public OnAmountReceivedEvent(Amount amount) {
        this.amount = amount;
    }

    public Amount getAmount() {
        return amount;
    }
}
