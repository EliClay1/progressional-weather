package net.displace.progressional_weather.storm;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.displace.progressional_weather.storm.enums.StormDirection;
import net.displace.progressional_weather.storm.enums.StormType;

import java.util.Map;
import java.util.Optional;

// TODO - Create an object for property config like lightning - only allow certain property values.

public record Storm(
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
    public Storm {
        if (minDuration > maxDuration) {
            throw new IllegalArgumentException("min_duration cannot be greater than max_duration!");
        }
        if (minDuration <= 0) {
            throw new IllegalArgumentException("min_duration & max_duration cannot be less than 0!");
        }
    }

    // defines the min and max validation range for storm tiers.
    private static final Codec<Integer> TIER_CODEC = Codec.intRange(1, 3);

    public static Codec<Storm> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(Storm::id),
                    StormType.CODEC.fieldOf("type").forGetter(Storm::type),
                    TIER_CODEC.fieldOf("tier").orElse(1).forGetter(Storm::tier),
                    StormDirection.CODEC.fieldOf("direction").forGetter(Storm::direction),
                    Codec.FLOAT.fieldOf("wind_strength").forGetter(Storm::windStrength),
                    Codec.INT.fieldOf("min_duration").forGetter(Storm::minDuration),
                    Codec.INT.fieldOf("max_duration").forGetter(Storm::maxDuration),
                    LightningConfig.CODEC.optionalFieldOf("lightning").forGetter(Storm::lightning),
                    Codec.BOOL.fieldOf("can_escalate").forGetter(Storm::canEscalate),
                    Codec.BOOL.fieldOf("naturally_generates").forGetter(Storm::naturallyGenerates),
                    Codec.unboundedMap(Codec.STRING, Codec.PASSTHROUGH)
                            .optionalFieldOf("properties", Map.of()).forGetter(Storm::properties)
            ).apply(instance, Storm::new)
    );
}

