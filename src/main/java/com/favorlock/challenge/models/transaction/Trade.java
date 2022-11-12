package com.favorlock.challenge.models.transaction;

import com.favorlock.challenge.enums.TradeActions;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString(callSuper = true)
public class Trade extends Transaction {
    private TradeActions action;

    private BigDecimal price;

    private String ticker;

    private Long shares;

    @Override
    public String getStock() {
        return getTicker();
    }
}
