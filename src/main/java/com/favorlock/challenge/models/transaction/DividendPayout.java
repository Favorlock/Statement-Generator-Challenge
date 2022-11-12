package com.favorlock.challenge.models.transaction;

import com.favorlock.challenge.models.transaction.StockTransaction;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString(callSuper = true)
public class DividendPayout extends StockTransaction {
    private BigDecimal dividend;
}
