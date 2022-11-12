package com.favorlock.challenge.models;

import com.favorlock.challenge.enums.TradeActions;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Trade extends Action {
    private TradeActions action;

    private BigDecimal price;

    private String ticker;

    private Long shares;
}
