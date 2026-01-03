package net.displace.progressional_weather.mixin;

import net.displace.progressional_weather.ProgressionalWeather;
import net.displace.progressional_weather.storm.ClientWeatherRenderer;
import net.minecraft.client.renderer.CloudRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mixin(CloudRenderer.class)
public class CloudRendererMixin {
    private static final Logger LOGGER = LoggerFactory.getLogger(CloudRendererMixin.class);

    @ModifyVariable(
            method = "render",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private static int modifyCloudColor(int originalColor) {

        return ClientWeatherRenderer.getCurrentStorm()
                .map(storm -> {
                    float darkeningFactor = storm.getSkyDarkeningFactor();

                    // Extract ARGB components
                    int a = (originalColor >> 24) & 0xFF;
                    int r = (originalColor >> 16) & 0xFF;
                    int g = (originalColor >> 8) & 0xFF;
                    int b = originalColor & 0xFF;

                    // Darken the cloud color based on storm intensity
                    r = (int) (r * (1.0f - darkeningFactor * 0.7f));
                    g = (int) (g * (1.0f - darkeningFactor * 0.7f));
                    b = (int) (b * (1.0f - darkeningFactor * 0.8f));

                    // Clamp to valid range
                    r = Math.max(0, Math.min(255, r));
                    g = Math.max(0, Math.min(255, g));
                    b = Math.max(0, Math.min(255, b));

                    // Recombine ARGB
                    int darkened = (a << 24) | (r << 16) | (g << 8) | b;
//                    LOGGER.info("Color modified: {} -> {}", originalColor, darkened);
                    return darkened;
                })
                .orElse(originalColor);
    }
}
