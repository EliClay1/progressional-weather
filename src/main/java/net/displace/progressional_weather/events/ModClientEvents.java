package net.displace.progressional_weather.events;

import net.displace.progressional_weather.ProgressionalWeather;
import net.displace.progressional_weather.storm.dataobjects.ActiveStorm;
import net.displace.progressional_weather.storm.dataobjects.Storm;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistration;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = ProgressionalWeather.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Storm.STORM_REGISTRY_KEY, Storm.CODEC, Storm.CODEC);
        ProgressionalWeather.LOGGER.info("Successfully registered storms!");
    }


}
