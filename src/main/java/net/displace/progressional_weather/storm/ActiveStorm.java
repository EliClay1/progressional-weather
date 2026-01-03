package net.displace.progressional_weather.storm;

import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public class ActiveStorm {
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
}
