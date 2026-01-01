package net.displace.progressional_weather.storm.enums;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum StormDirection implements StringRepresentable {
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west"),
    NORTHEAST("northeast"),
    NORTHWEST("northwest"),
    SOUTHEAST("southeast"),
    SOUTHWEST("southwest");

    private final String name;

    StormDirection(String name) {
        this.name = name;
    }

    @Override
    public @NonNull String getSerializedName() {
        return name;
    }

    public static final Codec<StormDirection> CODEC = StringRepresentable.fromEnum(StormDirection::values);
}
