package net.displace.progressional_weather.storm.managers;

import net.displace.progressional_weather.storm.ActiveStorm;
import net.displace.progressional_weather.storm.Storm;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ActiveStormManager {
    private static Map<Level, ActiveStorm> ACTIVE_STORMS = new HashMap<>();

    public static void initializeStorm(Level level, Identifier stormId, int duration) {
        Storm stormData = StormDataManager.getStorm(stormId);
        if (stormData == null) {
            throw new IllegalArgumentException("Failed to create storm: " + stormId.getPath());
        }

        // creates the storm at the spawn point of the world. TODO - Change this to focus on where the player is at.
        Vec3 stormCenter = level.getRespawnData().pos().getCenter();

        ActiveStorm activeStorm = new ActiveStorm(stormId, stormData, stormCenter, duration);
        ACTIVE_STORMS.put(level, activeStorm);
    }

    // ensures that only 1 storm is active at a given time.
    public static Optional<ActiveStorm> getActiveStorm(Level level) {
        ActiveStorm activeStorm = ACTIVE_STORMS.get(level);
        // removes storm if it's no longer active.
        if (activeStorm != null && !activeStorm.isActive()) {
            ACTIVE_STORMS.remove(level);
            return Optional.empty();
        }
        return Optional.ofNullable(activeStorm);
    }

    public static void tickAllStorms() {
        ACTIVE_STORMS.values().removeIf(activeStorm -> {
                    activeStorm.tick();
                    return !activeStorm.isActive();
                });
    }

    public static void endStorm(Level level) {
        ACTIVE_STORMS.remove(level);
    }

    public static void clearAllStorms() {
        ACTIVE_STORMS.clear();
    }
}
