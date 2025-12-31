package net.displace.progressional_weather.storm;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class StormDataDeserializer implements JsonDeserializer<StormData> {
    @Override
    public StormData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        return new StormData(
                obj.get("id").getAsString(),
                StormType.valueOf(obj.get("type").getAsString()),
                obj.get("id").getAsInt(),
                obj.get("windStrength").getAsFloat(),
                obj.get("lightningPower").getAsInt(),
                obj.get("lightningFrequency").getAsFloat(),
                0,
                obj.get("canEscalate").getAsBoolean(),
                context.deserialize(obj.get("properties"), Map.class)
        );
    }
}