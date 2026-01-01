package net.displace.progressional_weather.storm;

import com.mojang.serialization.Dynamic;
import net.displace.progressional_weather.storm.enums.StormDirection;
import net.displace.progressional_weather.storm.enums.StormType;

import java.util.Map;
import java.util.Optional;

// TODO - add data validation.

public record StormData(
        String id,
        StormType type,
        int tier,
        StormDirection direction,
        float windStrength,
        int minDuration,
        int maxDuration,
        Optional<LightningConfig> lightning,
        boolean canEscalate,
        boolean naturallyGenerates,
        Map<String, Dynamic<?>> properties
) {
    public StormData {
        if (minDuration > maxDuration) {
            throw new IllegalArgumentException("min_duration cannot be greater than max_duration!");
        }
        if (minDuration <= 0 || maxDuration <= 0) {
            throw new IllegalArgumentException("min_duration & max_duration cannot be less than 0!");
        }
    }

}

