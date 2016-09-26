package com.softjourn.sj_coin.callbacks;

import com.softjourn.sj_coin.model.Amount;

/**
 * Created by Andriy Ksenych on 16.09.2016.
 */
public class OnAmountReceivedEvent {
    Amount amount;

    public OnAmountReceivedEvent(Amount amount) {
        this.amount = amount;
    }

    public Amount getAmount() {
        return amount;
    }
}
