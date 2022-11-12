package com.favorlock.challenge.enums;

import lombok.Getter;

public enum TradeActions {
    BUY("bought"),
    SELL("sold");

    @Getter
    private String text;

    TradeActions(String text) {
        this.text = text;
    }
}
