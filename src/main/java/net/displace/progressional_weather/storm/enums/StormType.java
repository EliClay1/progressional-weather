package net.displace.progressional_weather.storm.enums;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum StormType implements StringRepresentable {
    // Add more storm types here.
    THUNDERSTORM("thunderstorm");

    private final String name;
    StormType(String name) {
        this.name = name;
    }

    @Override
    public @NonNull String getSerializedName() {
        return name;
    }

    public static final Codec<StormType> CODEC = StringRepresentable.fromEnum(StormType::values);
}

