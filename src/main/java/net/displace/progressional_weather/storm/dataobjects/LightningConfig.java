package net.displace.progressional_weather.storm.dataobjects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record LightningConfig(float strikeChance, float damage) {
    public static final Codec<LightningConfig> CODEC = RecordCodecBuilder.create(
            instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("strike_chance").forGetter(LightningConfig::strikeChance),
                    Codec.FLOAT.fieldOf("damage").forGetter(LightningConfig::damage)
            ).apply(instance, LightningConfig::new)
            );
}
