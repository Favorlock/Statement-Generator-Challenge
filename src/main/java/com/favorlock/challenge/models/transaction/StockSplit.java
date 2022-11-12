package com.favorlock.challenge.models.transaction;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class StockSplit extends StockTransaction {
    private long split;
}
