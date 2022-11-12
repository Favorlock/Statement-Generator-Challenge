package com.favorlock.challenge;

import com.favorlock.challenge.gson.StockActionAdapter;
import com.favorlock.challenge.models.ActionLog;
import com.favorlock.challenge.models.StockAction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;

public class StatementGenerator {
    private static StatementGenerator instance;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    private Gson gson;

    public StatementGenerator() {
        gson = new GsonBuilder()
                .setDateFormat(dateFormat.toPattern())
                .registerTypeAdapter(StockAction.class, new StockActionAdapter())
                .create();
    }

    public String generateStatement(String path) throws IOException {
        Reader reader = Files.newBufferedReader(Path.of(path));
        ActionLog log = gson.fromJson(reader, ActionLog.class);

        // TODO: Implement statement generation.

        return "";
    }

    public static void main(String... args) {
        instance = new StatementGenerator();

        String statement;

        try {
            statement = instance.generateStatement("./input.json");
        } catch (Exception ex) {
            throw new RuntimeException("Could not locate input file or contents are corrupt.");
        }

        // TODO: do something with the generated statement.
    }
}
