package com.favorlock.challenge;

import com.favorlock.challenge.models.Account;
import com.favorlock.challenge.models.transaction.Transaction;
import com.favorlock.challenge.models.transaction.TransactionLog;
import com.favorlock.challenge.models.transaction.TransactionStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StatementGenerator {
    private final TransactionLog log;

    private final List<TransactionStatement> transactionStatements;

    private final Account account;

    public StatementGenerator(TransactionLog log) {
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
            Transaction transaction = stack.pop();

            if (date == null || !date.equals(TransactionStatement.SHORT_DATE_FORMAT.format(transaction.getDate()))) {
                process(transactionToProcess);

                transactionToProcess.clear();

                date = TransactionStatement.SHORT_DATE_FORMAT.format(transaction.getDate());
            }

            transactionToProcess.add(transaction);
        }

        process(transactionToProcess);
    }

    private void process(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            return;
        }

        List<Transaction> processed = transactions.stream().filter(account::process).toList();

        if (processed.isEmpty()) {
            return;
        }

        transactionStatements.add(new TransactionStatement(account, processed));
    }

    @Override
    public String toString() {
        if (transactionStatements.isEmpty()) {
            throw new RuntimeException("No statements found for input.");
        }

        StringBuilder builder = new StringBuilder();

        transactionStatements.forEach(statement -> builder.append(statement.toString()));

        return builder.toString();
    }
}
