package com.favorlock.challenge.models.transaction;

import com.favorlock.challenge.models.transaction.StockTransaction;
import com.favorlock.challenge.models.transaction.Trade;
import com.favorlock.challenge.models.transaction.Transaction;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@ToString
public class TransactionLog {
    private List<Trade> actions;

    @SerializedName("stock_actions")
    private List<StockTransaction> stockActions;

    public List<Transaction> getAllTransactionsAscending() {
        List<Transaction> actions = new ArrayList<>();

        // Add all actions to our new list.
        actions.addAll(this.actions);
        actions.addAll(this.stockActions);

        // Sort the actions from
        actions.sort(Comparator.comparing(Transaction::getDate, Comparator.reverseOrder()));

        return actions;
    }
}
