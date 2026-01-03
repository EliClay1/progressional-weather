package net.displace.progressional_weather.storm.managers;

import net.displace.progressional_weather.ProgressionalWeather;
import net.displace.progressional_weather.storm.dataobjects.Storm;
import net.displace.progressional_weather.storm.enums.StormType;
import net.minecraft.resources.Identifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StormDataManager {
    private static final Map<Identifier, Storm> STORMS = new HashMap<>();

    public static Storm getStorm(Identifier id) {
        return STORMS.get(id);
    }

    public static Collection<Storm> getAllStorms() {
        return STORMS.values();
    }

    public static Collection<Storm> getStormsByType(StormType type) {
        return STORMS.values().stream()
                .filter(storm -> storm.type() == type)
                .collect(Collectors.toList());
    }

    public static Collection<Storm> getNaturallyGeneratingStorms() {
        return STORMS.values().stream()
                .filter(Storm::naturallyGenerates)
                .collect(Collectors.toList());
    }

    protected static void setStorms(Map<Identifier, Storm> storms) {
        STORMS.clear();
        STORMS.putAll(storms);
//        ProgressionalWeather.LOGGER.info("Loaded {} storms", STORMS.size());
    }

    protected static void clearStorms(){
        STORMS.clear();
    }
}
