package net.displace.progressional_weather.storm.dataobjects;

import io.netty.buffer.ByteBuf;
import net.displace.progressional_weather.ProgressionalWeather;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public class ActiveStorm implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ActiveStorm> TYPE = new
            CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(ProgressionalWeather.MODID, "weather_sync"));

    public static final StreamCodec<ByteBuf, ActiveStorm> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Identifier.CODEC),
            ActiveStorm::getStormId,
            ByteBufCodecs.fromCodec(Storm.CODEC),
            ActiveStorm::getStormData,
            ByteBufCodecs.fromCodec(Vec3.CODEC),
            ActiveStorm::getStormCenter,
            ByteBufCodecs.VAR_INT,
            ActiveStorm::getTicksRemaining,
            ActiveStorm::new
    );


    private final Identifier stormId;
    private final Storm stormData;
    private final Vec3 stormCenter;
    private int ticksRemaining;
    private int currentTier;
    private boolean isEscalating;

    public ActiveStorm(Identifier stormId, Storm stormData, Vec3 stormCenter, int duration) {
        this.stormId = stormId;
        this.stormData = stormData;
        this.stormCenter = stormCenter;
        this.ticksRemaining = duration;
        this.currentTier = stormData.tier();
        this.isEscalating = false;
    }

    public ActiveStorm createEmptyStorm() {
        return new ActiveStorm(
                Identifier.fromNamespaceAndPath(ProgressionalWeather.MODID, "none"),
                java.util.Optional.empty(),
                net.minecraft.world.phys.Vec3.ZERO,
                0,        // ticksRemaining = 0 → isActive() returns false
                0         // currentTier = 0
        );
    }

    public void tick() {
        ticksRemaining--;
    }

    public boolean isActive() {
        return ticksRemaining > 0;
    }

    public int getTicksRemaining() {
        return ticksRemaining;
    }

    public int getCurrentTier() {
        return currentTier;
    }

    public void setCurrentTier(int tier) {
        if (tier >= 1 && tier <= 3 && stormData.canEscalate()) {
            this.currentTier = tier;
        }
    }

    public Vec3 getStormCenter() {
        return stormCenter;
    }

    public Identifier getStormId() {
        return stormId;
    }

    public float getWindStrength() {
        return stormData.windStrength();
    }

    public float getSkyDarkeningFactor() {
        return 0.2f + (currentTier * 0.2f);
    }

    public Storm getStormData() {
        return stormData;
    }

    public boolean isEscalating() {
        return isEscalating;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
