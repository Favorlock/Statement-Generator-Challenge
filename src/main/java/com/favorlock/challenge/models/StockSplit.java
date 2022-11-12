package com.favorlock.challenge.models;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class StockSplit extends StockAction {
    private Long split;
}
