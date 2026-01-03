package net.displace.progressional_weather.storm;

import net.displace.progressional_weather.ProgressionalWeather;
import net.displace.progressional_weather.storm.dataobjects.ActiveStorm;
import net.displace.progressional_weather.storm.dataobjects.Storm;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.Optional;

public class WeatherSyncPacket implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<WeatherSyncPacket> TYPE = new CustomPacketPayload.Type<>(
            Identifier.fromNamespaceAndPath(ProgressionalWeather.MODID, "weather_sync")
    );

    private final Storm stormData;

    public WeatherSyncPacket(Storm stormData) {
        this.stormData = stormData;
    }

    public WeatherSyncPacket(ActiveStorm activeStorm) {
        this.stormData = activeStorm.getStormData();
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return null;
    }
}
