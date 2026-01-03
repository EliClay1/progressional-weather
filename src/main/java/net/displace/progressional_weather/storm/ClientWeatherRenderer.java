package net.displace.progressional_weather.storm;

import com.mojang.blaze3d.systems.RenderSystem;
import net.displace.progressional_weather.ProgressionalWeather;
import net.displace.progressional_weather.storm.dataobjects.ActiveStorm;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

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

//    @SubscribeEvent
//    public static void onRenderFog(ViewportEvent.RenderFog event) {
//        currentStorm.ifPresent(storm -> {
//            float darkeningFactor = storm.getSkyDarkeningFactor();
//
//            // Reduce visibility during storms
//            float fogDistance = 100.0f - (darkeningFactor * 100.0f);
//
//            event.setNearPlaneDistance(0.1f);
//            event.setFarPlaneDistance(fogDistance);
//        });
//    }

    public static float getStormSkyColorModifier() {
        return currentStorm
                .map(storm -> 1.0f - storm.getSkyDarkeningFactor())
                .orElse(1.0f);
    }
}
