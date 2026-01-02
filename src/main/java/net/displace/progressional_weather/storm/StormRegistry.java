package net.displace.progressional_weather.storm;

import net.displace.progressional_weather.ProgressionalWeather;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = ProgressionalWeather.MODID)
public class StormRegistry {
    public static final ResourceKey<Registry<Storm>> STORM_REGISTRY_KEY =
            ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(ProgressionalWeather.MODID, "storms"));

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(STORM_REGISTRY_KEY, Storm.CODEC, Storm.CODEC);
        ProgressionalWeather.LOGGER.info("Registered storms!");
    }
}
