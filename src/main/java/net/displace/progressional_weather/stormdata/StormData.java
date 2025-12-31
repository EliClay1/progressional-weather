package net.displace.progressional_weather.stormdata;

import net.minecraft.world.phys.Vec3;

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

