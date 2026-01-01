package net.displace.progressional_weather.storm;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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

    // defines the min and max validation range for storm tiers.
    private static final Codec<Integer> TIER_CODEC = Codec.intRange(1, 3);

    public static Codec<StormData> STORM_DATA_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(StormData::id),
                    StormType.CODEC.fieldOf("type").forGetter(StormData::type),
                    TIER_CODEC.fieldOf("tier").orElse(1).forGetter(StormData::tier),
                    StormDirection.CODEC.fieldOf("direction").forGetter(StormData::direction),
                    Codec.FLOAT.fieldOf("wind_strength").forGetter(StormData::windStrength),
                    Codec.INT.fieldOf("min_duration").forGetter(StormData::minDuration),
                    Codec.INT.fieldOf("max_duration").forGetter(StormData::maxDuration),
                    LightningConfig.CODEC.optionalFieldOf("lightning").forGetter(StormData::lightning),
                    Codec.BOOL.fieldOf("can_escalate").forGetter(StormData::canEscalate),
                    Codec.BOOL.fieldOf("naturally_generates").forGetter(StormData::naturallyGenerates),
                    Codec.unboundedMap(Codec.STRING, Codec.PASSTHROUGH)
                            .optionalFieldOf("properties", Map.of()).forGetter(StormData::properties)
            ).apply(instance, StormData::new)
    );

}

