package com.favorlock.challenge.models;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public abstract class StockAction extends Action {
    private String stock;
}
