package com.favorlock.challenge.models.transaction;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public abstract class StockTransaction extends Transaction {
    private String stock;
}
