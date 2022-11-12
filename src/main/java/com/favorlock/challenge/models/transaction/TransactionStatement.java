package com.favorlock.challenge.models.transaction;

import com.favorlock.challenge.enums.TradeActions;
import com.favorlock.challenge.models.Account;
import com.favorlock.challenge.models.Share;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

public class TransactionStatement {
    public static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final DecimalFormat MONEY_FORMAT = new DecimalFormat("$###.00");

    private final String contents;

    public TransactionStatement(Account account, List<Transaction> transaction) {
        this.contents = generateContents(account, transaction);
    }

    private String generateContents(Account account, List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            throw new RuntimeException("No actions provided when generating transaction statement contents.");
        }

        StringBuilder builder = new StringBuilder();

        builder.append(String.format("On %s, you have:\n", SHORT_DATE_FORMAT.format(transactions.get(0).getDate())));

        account.getShares().values().stream()
                .filter(transaction -> transaction.getHeld() > 0)
                .sorted(Comparator.comparing(Share::getHeld, Comparator.reverseOrder()))
                .forEach((share -> builder.append(String.format(
                        "    - %d shares of %s at %s per share\n",
                        share.getHeld(),
                        share.getStock(),
                        MONEY_FORMAT.format(share.getPrice())
                ))));

        String dividends = account.getDividendIncome().compareTo(BigDecimal.ZERO) == 0 ? "$0" : MONEY_FORMAT.format(account.getDividendIncome());
        builder.append(String.format("    - %s of dividend income\n", dividends));

        builder.append("  Transactions:\n");
        for (Transaction action : transactions) {
            generateTransactionMessage(account, action, builder);
        }

        return builder.toString();
    }

    private void generateTransactionMessage(Account account, Transaction transaction, StringBuilder builder) {
        if (transaction instanceof Trade trade) {
            if (trade.getAction() == TradeActions.BUY) {
                builder.append(String.format(
                        "    - You %s %d shares of %s at %s per share\n",
                        trade.getAction().getText(),
                        trade.getShares(),
                        trade.getTicker(),
                        MONEY_FORMAT.format(trade.getPrice())
                ));
            } else {
                if (!account.getShares().containsKey(trade.getStock())) {
                    return;
                }

                Share share = account.getShares().get(trade.getStock());
                BigDecimal tradeValue = trade.getPrice().multiply(BigDecimal.valueOf(trade.getShares()));
                BigDecimal purchaseValue = share.getPrice().multiply(BigDecimal.valueOf(trade.getShares()));
                BigDecimal income = tradeValue.subtract(purchaseValue);

                builder.append(String.format(
                        "    - You %s %d shares of %s at %s per share for a %s of %s\n",
                        trade.getAction().getText(),
                        trade.getShares(),
                        trade.getTicker(),
                        MONEY_FORMAT.format(trade.getPrice()),
                        income.compareTo(BigDecimal.ZERO) < 0 ? "loss" : "profit",
                        MONEY_FORMAT.format(income)
                ));
            }
        } else if (transaction instanceof DividendPayout payout) {
            builder.append(String.format(
                    "    - %s paid out %s dividend per share, and you have %d shares\n",
                    payout.getStock(),
                    MONEY_FORMAT.format(payout.getDividend()),
                    account.getShares().get(payout.getStock()).getHeld()
            ));
        } else if (transaction instanceof StockSplit split) {
            builder.append(String.format(
                    "    - %s split %d to 1, and you have %d shares\n",
                    split.getStock(),
                    split.getSplit(),
                    account.getShares().get(split.getStock()).getHeld()
            ));
        }
    }

    @Override
    public String toString() {
        return contents;
    }
}
