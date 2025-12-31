package net.displace.progressional_weather.storm;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

public class LevelStormData {
    private final ServerLevel level;
    private final StormManager stormManager;

    public LevelStormData(ServerLevel level) {
        this.level = level;
        this.stormManager = new StormManager(level);
    }

    public StormManager getStormManager() {
        return stormManager;
    }

    public void saveToTag(CompoundTag tag) {
        if (stormManager.hasActiveStorm()) {
            tag.putString("activeStormId", stormManager.getActiveStorm().getData().stormId());
            tag.putInt("remainingDuration", stormManager.getActiveStorm().getRemainingDuration());
        }
    }
}
