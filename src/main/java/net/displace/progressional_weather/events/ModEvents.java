package net.displace.progressional_weather.events;

import net.displace.progressional_weather.ProgressionalWeather;
import net.displace.progressional_weather.command.StormDebugCommand;
import net.displace.progressional_weather.storm.Storm;
import net.displace.progressional_weather.storm.StormDataLoader;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

@EventBusSubscriber(modid = ProgressionalWeather.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        new StormDebugCommand(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());

        ProgressionalWeather.LOGGER.info("Successfully registered commands.");
    }

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Storm.STORM_REGISTRY_KEY, Storm.CODEC, Storm.CODEC);
        ProgressionalWeather.LOGGER.info("Registered storms!");
    }

    @SubscribeEvent
    public static void onDatapackReload(AddServerReloadListenersEvent event) {
        event.addListener(Storm.STORM_REGISTRY_KEY.identifier(), new StormDataLoader());
    }
}
