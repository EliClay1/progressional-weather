package net.displace.progressional_weather.storm;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.displace.progressional_weather.storm.enums.StormDirection;
import net.displace.progressional_weather.storm.enums.StormType;

import java.util.Map;

public class StormCodec {

    // defines the min and max validation range for storm tiers.
    private static final Codec<Integer> TIER_CODEC = Codec.intRange(1, 3);

    Codec<StormData> STORM_DATA_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(StormData::id),
                    StormType.CODEC.fieldOf("type").forGetter(StormData::type),
                    TIER_CODEC.optionalFieldOf("tier", 1).forGetter(StormData::tier),
                    StormDirection.CODEC.fieldOf("direction").forGetter(StormData::direction),
                    Codec.FLOAT.fieldOf("wind_strength").forGetter(StormData::windStrength),
                    Codec.INT.fieldOf("min_duration").forGetter(StormData::minDuration),
                    Codec.INT.fieldOf("max_duration").forGetter(StormData::maxDuration),
                    LightningConfig.CODEC.listOf().fieldOf("lightning").forGetter(StormData::lightning),
                    Codec.BOOL.fieldOf("can_escalate").forGetter(StormData::canEscalate),
                    Codec.BOOL.fieldOf("naturally_generates").forGetter(StormData::naturallyGenerates),
                    Codec.unboundedMap(Codec.STRING, Codec.PASSTHROUGH)
                            .optionalFieldOf("properties", Map.of()).forGetter(StormData::properties)
            ).apply(instance, StormData::new)
    );
}
