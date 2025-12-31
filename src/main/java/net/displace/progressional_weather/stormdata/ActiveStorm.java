package net.displace.progressional_weather.stormdata;

import net.minecraft.world.phys.Vec3;

public class ActiveStorm {
    private StormData stormData;
    private int ticksExisted;
    private Vec3 windVector;
    private boolean isEscalating;

    public ActiveStorm(StormData stormData) {
        this.stormData = stormData;
        this.ticksExisted = 0;
        this.windVector = Vec3.ZERO;
        this.isEscalating = false;
    }

    public StormData getData() { return stormData; }
    public StormType getType() { return stormData.type(); }
    public int getTier() { return stormData.tier(); }
    public Vec3 getWindVector() { return windVector; }

    public void tick() {
        ticksExisted++;
        updateWindVector();
        checkEscalation();
    }

    public int getRemainingDuration() {
        return stormData.duration() - ticksExisted;
    }

    public boolean isActive() {
        return getRemainingDuration() > 0;
    }

    private void updateWindVector() {

    }

    private void checkEscalation() {

    }

}
