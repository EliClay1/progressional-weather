package net.displace.progressional_weather.stormdata;

import net.displace.progressional_weather.ProgressionalWeather;
import net.minecraft.server.level.ServerLevel;

import java.util.Collection;

public class StormManager {
    private final ServerLevel level;
    private ActiveStorm activeStorm;
    private int nextStormTick;

    public StormManager(ServerLevel level) {
        this.level = level;
        this.activeStorm = null;
        this.nextStormTick = 0;
    }

    public void tick() {
        if (activeStorm != null && activeStorm.isActive()) {
            activeStorm.tick();
        } else if (activeStorm != null) {
            endStorm();
        }
        if (activeStorm == null) {
            nextStormTick--;
            if (nextStormTick <= 0) {
                spawnRandomStorm();
            }
        }
    }

    public void spawnStorm(String stormId, int duration) {
        if (activeStorm != null && activeStorm.isActive()) {
            return;
        }
        activeStorm = StormRegistry.createActiveStorm(stormId, duration);
        ProgressionalWeather.LOGGER.info("Storm started in {}: {} (duration: {} ticks)", level.dimension(), stormId, duration);
        syncToClients();
    }

    public void spawnRandomStorm() {
        Collection<StormData> allStorms = StormRegistry.getAllStorms();
        if (allStorms.isEmpty()) {
            return;
        }
        StormData randomStorm = allStorms.stream()
                .skip(level.random.nextInt(allStorms.size()))
                .findFirst()
                .orElse(null);

        if (randomStorm != null) {
            int duration = 3000 + level.random.nextInt(4000);
            spawnStorm(randomStorm.stormId(), duration);
            nextStormTick = duration + 6000 + level.random.nextInt(12000);
        }
    }

    public void endStorm() {
        if (activeStorm != null) {
            ProgressionalWeather.LOGGER.info("Storm ended in {}: {}", level.dimension(), activeStorm.getData().stormId());
            activeStorm = null;
            syncToClients();
        }
    }

    public ActiveStorm getActiveStorm() {
        return activeStorm;
    }

    public boolean hasActiveStorm() {
        return activeStorm != null && activeStorm.isActive();
    }

    public int getStormTier() {
        return activeStorm != null ? activeStorm.getTier(): 0;
    }

    private void syncToClients() {
        // TODO implement this.
    }


}
