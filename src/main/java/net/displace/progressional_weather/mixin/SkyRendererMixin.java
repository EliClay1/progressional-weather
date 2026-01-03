package net.displace.progressional_weather.mixin;

import net.displace.progressional_weather.ProgressionalWeather;
import net.displace.progressional_weather.storm.ClientWeatherRenderer;
import net.minecraft.client.renderer.SkyRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SkyRenderer.class)
public class SkyRendererMixin {
    @ModifyVariable(
            method = "renderSkyDisc",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    )
    private int modifySkyColor(int color) {
        return ClientWeatherRenderer.getCurrentStorm()
                .map(storm -> {
                    float darkeningFactor = storm.getSkyDarkeningFactor();

                    // Extract RGB (ignore alpha)
                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = color & 0xFF;

                    // Darken the sky
                    r = (int) (r * (1.0f - darkeningFactor * 0.8f));
                    g = (int) (g * (1.0f - darkeningFactor * 0.8f));
                    b = (int) (b * (1.0f - darkeningFactor * 0.8f));

                    // Clamp
                    r = Math.max(0, Math.min(255, r));
                    g = Math.max(0, Math.min(255, g));
                    b = Math.max(0, Math.min(255, b));

                    // Recombine
                    return (r << 16) | (g << 8) | b;
                })
                .orElse(color);
    }
}
