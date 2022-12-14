package com.favorlock.challenge;

import com.favorlock.challenge.gson.StockTransactionAdapter;
import com.favorlock.challenge.models.transaction.TransactionLog;
import com.favorlock.challenge.models.transaction.StockTransaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;

public class Application {
    private static Application instance;

    private Gson gson;

    public Application() {
        gson = new GsonBuilder()
                .setDateFormat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").toPattern())
                .registerTypeAdapter(StockTransaction.class, new StockTransactionAdapter())
                .create();
    }

    public String generateStatement(String path) {
        Reader reader;

        try {
            reader = Files.newBufferedReader(Path.of(path));
        } catch (Exception ex) {
            throw new RuntimeException("Could not locate input file or contents are corrupt.");
        }

        // Deserialize the input data to TransactionLog type using GSON.
        TransactionLog log = gson.fromJson(reader, TransactionLog.class);
        // Generate statement from transaction log.
        AccountStatement statement = new AccountStatement(log);

        return statement.toString();
    }

    public static void main(String... args) {
        instance = new Application();

        // Just printing the output to console for now.
        System.out.println(instance.generateStatement("./input.json"));
    }
}
