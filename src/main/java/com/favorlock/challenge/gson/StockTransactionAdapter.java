package com.favorlock.challenge.gson;

import com.favorlock.challenge.models.transaction.DividendPayout;
import com.favorlock.challenge.models.transaction.StockSplit;
import com.favorlock.challenge.models.transaction.StockTransaction;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

public class StockTransactionAdapter implements JsonDeserializer<StockTransaction> {
    private final Gson gson = new GsonBuilder()
            .setDateFormat(new SimpleDateFormat("yyyy/MM/dd").toPattern())
            .create();

    @Override
    public StockTransaction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Determine the correct type to deserialize.
        JsonObject obj = json.getAsJsonObject();

        if (!obj.get("dividend").getAsString().isBlank()) {
            return gson.fromJson(obj, DividendPayout.class);
        }

        return gson.fromJson(obj, StockSplit.class);
    }
}
