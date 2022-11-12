package com.favorlock.challenge.gson;

import com.favorlock.challenge.models.DividendPayout;
import com.favorlock.challenge.models.StockSplit;
import com.favorlock.challenge.models.StockAction;
import com.google.gson.*;

import java.lang.reflect.Type;

public class StockActionAdapter implements JsonDeserializer<StockAction> {
    @Override
    public StockAction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        if (!obj.get("dividend").getAsString().isBlank()) {
            return context.deserialize(obj, DividendPayout.class);
        }

        return context.deserialize(obj, StockSplit.class);
    }
}
