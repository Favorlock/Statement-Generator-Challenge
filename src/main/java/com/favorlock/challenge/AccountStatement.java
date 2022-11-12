package com.favorlock.challenge;

import com.favorlock.challenge.models.Account;
import com.favorlock.challenge.models.transaction.Transaction;
import com.favorlock.challenge.models.transaction.TransactionLog;
import com.favorlock.challenge.models.transaction.TransactionStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AccountStatement {
    private final TransactionLog log;

    private final List<TransactionStatement> transactionStatements;

    private final Account account;

    public AccountStatement(TransactionLog log) {
        this.log = log;
        this.transactionStatements = new ArrayList<>();
        this.account = new Account();

        generate();
    }

    private void generate() {
        Stack<Transaction> stack = new Stack<>();

        stack.addAll(log.getAllTransactionsAscending());

        String date = null;
        List<Transaction> transactionToProcess = new ArrayList<>();

        while (!stack.isEmpty()) {
            // Pop the next transaction to check from the stack.
            Transaction transaction = stack.pop();

            /*
             If the previous date is null or a different date altogether we must process the next batch of transactions,
             clear the list, and set the date for the current transaction.
             */
            if (date == null || !date.equals(TransactionStatement.SHORT_DATE_FORMAT.format(transaction.getDate()))) {
                process(transactionToProcess);

                transactionToProcess.clear();

                date = TransactionStatement.SHORT_DATE_FORMAT.format(transaction.getDate());
            }

            // Queue the current transaction for processing.
            transactionToProcess.add(transaction);
        }

        // Process remaining transactions.
        process(transactionToProcess);
    }

    private void process(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return;
        }

        // Filter transactions to those that affect the account.
        List<Transaction> processed = transactions.stream().filter(account::process).toList();

        if (processed.isEmpty()) {
            return;
        }

        // Add a transaction statement for the processed transactions.
        transactionStatements.add(new TransactionStatement(account, processed));
    }

    @Override
    public String toString() {
        if (transactionStatements.isEmpty()) {
            throw new RuntimeException("No statements found for input.");
        }

        StringBuilder builder = new StringBuilder();

        // Generate account statement from transaction statements.
        transactionStatements.forEach(statement -> builder.append(statement.toString()));

        return builder.toString();
    }
}
