package com.favorlock.challenge.models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ActionLog {
    private List<Trade> actions;

    @SerializedName("stock_actions")
    private List<StockAction> stockActions;
}
