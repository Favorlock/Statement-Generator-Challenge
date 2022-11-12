package com.favorlock.challenge.models;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString(callSuper = true)
public class DividendPayout extends StockAction {
    private BigDecimal dividend;
}
