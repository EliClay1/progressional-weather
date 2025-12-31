package net.displace.progressional_weather.storm;

import net.displace.progressional_weather.storm.enums.Direction;
import net.displace.progressional_weather.storm.enums.StormType;

import java.lang.reflect.Array;
import java.util.Map;

public record StormData(
        String stormId,
        StormType type,
        int tier,
        Direction direction,
        float windStrength,
        int[] durationRange,
        Array lightning,
        boolean canEscalate,
        boolean naturallyGenerates,
        Map<String, Object> properties
) {}

