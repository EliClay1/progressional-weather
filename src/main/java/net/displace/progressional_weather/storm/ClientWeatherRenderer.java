package net.displace.progressional_weather.storm;

import net.displace.progressional_weather.ProgressionalWeather;
import net.displace.progressional_weather.storm.dataobjects.ActiveStorm;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.Optional;

@EventBusSubscriber(modid = ProgressionalWeather.MODID, value = Dist.CLIENT)
public class ClientWeatherRenderer {
    private static Optional<ActiveStorm> currentStorm = Optional.empty();

    public static void handleWeatherSync(ActiveStorm activeStorm) {
        if (activeStorm.isActive()) {
            currentStorm = Optional.of(activeStorm);
        } else {
            currentStorm = Optional.empty();
        }
    }

    public static Optional<ActiveStorm> getCurrentStorm() {
        return currentStorm;
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent.AfterLevel event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
                return;
        }
        currentStorm.ifPresent(ClientWeatherRenderer::applySkyDarkening);
    }

    private static void applySkyDarkening(ActiveStorm stormData) {

        float darkeningFactor = stormData.getSkyDarkeningFactor();

        // This is a simple approach - you can expand this significantly
        // For now, we'll modify the fog color and brightness

        // Note: Full sky darkening requires modifying sky rendering
        // For now, this is a placeholder showing where you'd apply effects

        ProgressionalWeather.LOGGER.debug("Storm tier {} at {}",
                stormData.getCurrentTier(), stormData.getStormCenter());
    }
}
