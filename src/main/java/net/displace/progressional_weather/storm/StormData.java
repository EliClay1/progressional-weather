package net.displace.progressional_weather.storm;

import java.util.Map;

public record StormData(
        String stormId,
        StormType type,
        int tier,
        float windStrength,
        int lightningFrequency,
        float lightningPower,
        int duration,
        boolean canEscalate,
        Map<String, Object> properties
) {}

