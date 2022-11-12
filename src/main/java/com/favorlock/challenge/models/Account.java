package com.favorlock.challenge.models;

import com.favorlock.challenge.enums.TradeActions;
import com.favorlock.challenge.models.transaction.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Account {
    private Map<String, Share> shares = new HashMap<>();
    private BigDecimal dividendIncome = BigDecimal.ZERO;

    public boolean process(Transaction transaction) {
        if (transaction instanceof StockTransaction && !shares.containsKey(transaction.getStock())) {
            return false;
        }

        if (transaction instanceof Trade trade) {
            this.trade(trade);
        } else if (transaction instanceof DividendPayout payout) {
            this.dividendPayout(payout);
        } else if (transaction instanceof StockSplit split) {
            this.split(split);
        }

        return true;
    }

    public void trade(Trade transaction) {
        if (!shares.containsKey(transaction.getStock())) {
            if (transaction.getAction() == TradeActions.BUY) {
                shares.put(transaction.getStock(), new Share(
                        transaction.getStock(),
                        transaction.getShares(),
                        transaction.getPrice()
                ));
            }

            return;
        }

        Share share = shares.get(transaction.getTicker());

        share.trade(transaction);
    }

    public void dividendPayout(DividendPayout transaction) {
        Share share = shares.get(transaction.getStock());

        dividendIncome = dividendIncome.add(transaction.getDividend().multiply(BigDecimal.valueOf(share.getHeld())));
    }

    public void split(StockSplit transaction) {
        Share share = shares.get(transaction.getStock());

        // Split the held amount and price to reflect the stock split.
        share.split(transaction);
    }
}
