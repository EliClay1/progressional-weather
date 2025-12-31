package net.displace.progressional_weather.storm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.displace.progressional_weather.ProgressionalWeather;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StormRegistry {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(StormData.class, new StormDataDeserializer())
            .create();
    private static final Map<String, StormData> STORMS = new HashMap<>();


    public static void loadStorms(ResourceManager resourceManager) {
        STORMS.clear();

        resourceManager.listResources("storms", path -> path.getPath().endsWith(".json"))
                .forEach((location, resource) -> {
                    try (InputStream inputStream = resource.open()) {
                        String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                        StormData stormData = GSON.fromJson(json, StormData.class);
                        STORMS.put(stormData.stormId(), stormData);
                        ProgressionalWeather.LOGGER.info("Loaded storm: {}", stormData.stormId());
                    } catch (Exception e) {
                        ProgressionalWeather.LOGGER.error("Failed to load storm from: {}", location, e);
                    }
                });
    }

    public static StormData getStorm(String stormId) {
        return STORMS.get(stormId);
    }

    public static Collection<StormData> getAllStorms() {
        return STORMS.values();
    }

    public static ActiveStorm createActiveStorm(String stormId, int duration) {
        StormData baseData = getStorm(stormId);
        if (baseData == null) {
            throw new IllegalArgumentException("Unknown storm: " + stormId);
        }
        return new ActiveStorm(
                new StormData(
                        baseData.stormId(),
                        baseData.type(),
                        baseData.tier(),
                        baseData.windStrength(),
                        baseData.lightningFrequency(),
                        baseData.lightningPower(),
                        duration,
                        baseData.canEscalate(),
                        baseData.properties()
                )
        );
    }
}
