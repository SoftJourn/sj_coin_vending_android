package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.api_models.Amount;

public class OnAmountReceivedEvent {
    private final Amount amount;

    public OnAmountReceivedEvent(Amount amount) {
        this.amount = amount;
    }

    public Amount getAmount() {
        return amount;
    }
}
