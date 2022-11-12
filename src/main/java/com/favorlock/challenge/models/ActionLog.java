package com.favorlock.challenge.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActionLog {
    private List<Trade> actions;

    @SerializedName("stock_actions")
    private List<StockAction> stockActions;
}
