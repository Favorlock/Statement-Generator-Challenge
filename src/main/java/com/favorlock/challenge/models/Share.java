package com.favorlock.challenge.models;

import com.favorlock.challenge.models.transaction.StockSplit;
import com.favorlock.challenge.models.transaction.Trade;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@AllArgsConstructor
public class Share {
    private String stock;
    private long held;
    private BigDecimal price;

    public void split(StockSplit split) {
        held *= split.getSplit();
        price = price.divide(BigDecimal.valueOf(split.getSplit()), RoundingMode.UP);
    }

    public void trade(Trade trade) {
        switch (trade.getAction()) {
            case BUY:
                held += trade.getShares();
                break;
            case SELL:
                held -= trade.getShares();
            default:
                break;
        }
    }
}
